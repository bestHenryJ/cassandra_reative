package com.henry.reactivecassandrademo.repository;

import com.henry.reactivecassandrademo.dto.ProductDto;
import com.henry.reactivecassandrademo.entity.Product;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCassandraRepository<Product, Integer> {
    @AllowFiltering
    Flux<ProductDto> findByPriceBetween(Range<Double> closed);

}
