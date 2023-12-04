package com.henry.reactivecassandrademo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @PrimaryKey
    private int id;
    private String name;
    private int qty;
    private double price;
}
