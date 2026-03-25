package com.aloha.product.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    private String id = UUID.randomUUID().toString();
    private String name;
    private int price;
    private int stock;
    private LocalDateTime createdAt;

}
