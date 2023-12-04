package com.henry.reactivecassandrademo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class ProductDto {
    private int id;
    private String name;
    private int qty;
    private double price;
}
