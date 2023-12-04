package com.henry.reactivecassandrademo.utils;

import com.henry.reactivecassandrademo.dto.ProductDto;
import com.henry.reactivecassandrademo.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUntils {
    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }
    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
