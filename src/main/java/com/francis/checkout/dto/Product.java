package com.francis.checkout.dto;

import lombok.Data;

import java.util.List;

@Data
public class Product {

    private String id;
    private String name;
    private Double price;
    private List<Promotion> promotions;
}

