package com.francis.checkout.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "tbl_checkout")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(value = "item-quantity")
    private int itemsQuantity;

    private int discount;

    @JsonProperty(value = "total-price")
    private Double totalPrice;

    @JsonProperty(value = "product-id")
    private String productId;
}