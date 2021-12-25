package com.francis.checkout.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BasketUpdateRequest {

    @JsonIgnore
    private String Id;

    @NotNull(message = "Product quantity is mandatory")
    private int quantity;
}
