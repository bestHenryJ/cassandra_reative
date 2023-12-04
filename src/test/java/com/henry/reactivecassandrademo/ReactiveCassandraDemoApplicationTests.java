package com.henry.reactivecassandrademo;

import com.henry.reactivecassandrademo.controller.ProductController;
import com.henry.reactivecassandrademo.dto.ProductDto;
import com.henry.reactivecassandrademo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class ReactiveCassandraDemoApplicationTests {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ProductService productService;

    @Test
    public void addProductTest(){
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto(5624, "mobile", 3, 2500));
        when(productService.saveProduct(productDtoMono)).thenReturn(productDtoMono);

        webTestClient.post().uri("/products")
                .body(Mono.just(productDtoMono), ProductDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getProductsTest(){
        Flux<ProductDto> productDtoFlux = Flux.just(new ProductDto(5697, "mobile", 9, 1700), new ProductDto(8747, "TV", 3, 10000));
        when(productService.getProducts()).thenReturn(productDtoFlux);

        Flux<ProductDto> responseBody = webTestClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();
        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductDto(5697, "mobile", 9, 1700))
                .expectNext(new ProductDto(8747, "TV", 3, 10000))
                .verifyComplete();
    }
    @Test
    public void getProductByIdTest(){
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto(5697, "mobile", 9, 1700));
        when(productService.getProduct(any())).thenReturn(productDtoMono);

        Flux<ProductDto> responseBody = webTestClient.get().uri("/products/5697")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();
        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getName().equals("mobile"))
                .verifyComplete();
    }
    @Test
    public void updateProductTest(){
        Mono<ProductDto> productDtoMono = Mono.just(new ProductDto(5697, "mobile", 9, 1700));
        when(productService.updateProduct(productDtoMono, 5697)).thenReturn(productDtoMono);
        webTestClient.put().uri("/products/update/102")
                .body(Mono.just(productDtoMono), ProductDto.class)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    public void deleteProductTest(){
        given(productService.deleteProduct(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/products/5697")
                .exchange()
                .expectStatus().isOk();
    }
}
