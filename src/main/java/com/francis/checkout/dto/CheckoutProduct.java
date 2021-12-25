package com.francis.checkout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckoutProduct {

    private String name;
    private Double price;
    private Integer quantity;

    @JsonProperty(value = "product-id")
    private String productId;

    @JsonProperty(value = "price-with-promotion")
    private Double priceWithPromotion = new Double("0.0");

    private Double discount = new Double("0.0");
}
