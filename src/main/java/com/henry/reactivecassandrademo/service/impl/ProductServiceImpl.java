package com.henry.reactivecassandrademo.service.impl;

import com.henry.reactivecassandrademo.dto.ProductDto;
import com.henry.reactivecassandrademo.repository.ProductRepository;
import com.henry.reactivecassandrademo.service.ProductService;
import com.henry.reactivecassandrademo.utils.AppUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<ProductDto> getProducts() {
        return productRepository.findAll().map(AppUntils::entityToDto);
    }

    @Override
    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono.map(AppUntils::dtoToEntity)
                .flatMap(productRepository::insert)
                .map(AppUntils::entityToDto);
    }

    @Override
    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, Integer id) {
        return productRepository.findById(id)
                .flatMap(p->productDtoMono.map(AppUntils::dtoToEntity))
                .doOnNext(e -> e.setId(id))
                .flatMap(productRepository::save)
                .map(AppUntils::entityToDto);
    }

    @Override
    public Flux<ProductDto> getProductInRange(double min, double max) {
        return productRepository.findByPriceBetween(Range.closed(min, max));
    }

    @Override
    public Mono<ProductDto> getProduct(Integer id) {
        return productRepository.findById(id).map(AppUntils::entityToDto);
    }

    @Override
    public Mono<Void> deleteProduct(Integer id) {
        return productRepository.deleteById(id);
    }
}
