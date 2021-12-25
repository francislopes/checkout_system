package com.francis.checkout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckoutTotal {

    private List<CheckoutProduct> products = new ArrayList<>();

    @JsonProperty(value = "raw-total")
    private Double rawTotal = new Double("0.0");; //total price without discount

    @JsonProperty(value = "total-promo-qty-based-price-override")
    private Double totalPromoQtyBasedPriceOverride = new Double("0.0");; //total discounts for Amazing Pizza!

    @JsonProperty(value = "total-promo-by-X-get-Y-free")
    private Double totalPromoByXGetYFree = new Double("0.0");; //total discounts for Amazing Burger!

    @JsonProperty(value = "total-promo-flat-percent")
    private Double totalPromoFlatPercent = new Double("0.0");; //total discounts for Amazing Salad!

    @JsonProperty(value = "total-promotions")
    private Double totalPromotions = new Double("0.0");; //total discount for all products

    @JsonProperty(value = "total-payable")
    private Double totalPayable = new Double("0.0");; //total price with discount
}
