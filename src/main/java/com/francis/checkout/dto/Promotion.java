package com.francis.checkout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Promotion {

    private String id;
    private String type;

    @JsonProperty(value = "required_qty")
    private Integer requiredQty;

    private Double price;
    private int amount;

    @JsonProperty(value = "free_qty")
    private int freeQty;
}
