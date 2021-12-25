package com.francis.checkout.controller;

import com.francis.checkout.dto.CheckoutTotal;
import com.francis.checkout.service.CheckoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Checkout Controller")
@RestController
@RequestMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @ApiOperation("Pay and clear the basket")
    @GetMapping("/pay-and-clear")
    public CheckoutTotal payAndClear() {
        return checkoutService.payAndClear();
    }

}
