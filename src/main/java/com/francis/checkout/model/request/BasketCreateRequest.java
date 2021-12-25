package com.francis.checkout.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BasketCreateRequest {

    @NotBlank(message = "Product ID is mandatory")
    @JsonProperty(value = "product-id")
    private String productId;

    @NotNull(message = "Product quantity is mandatory")
    private int quantity;

}
