package com.henry.reactivecassandrademo.service;

import com.henry.reactivecassandrademo.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductDto> getProducts();
    Mono<ProductDto> getProduct(Integer id);
    Flux<ProductDto> getProductInRange(double min, double max);
    Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono);
    Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, Integer id);

    Mono<Void> deleteProduct(Integer id);
}
