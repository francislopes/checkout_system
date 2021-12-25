package com.francis.checkout.controller;

import com.francis.checkout.model.Basket;
import com.francis.checkout.model.request.BasketCreateRequest;
import com.francis.checkout.model.request.BasketUpdateRequest;
import com.francis.checkout.service.BasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Basket Controller")
@RestController
@RequestMapping(value = "/basket", produces = MediaType.APPLICATION_JSON_VALUE)
public class BasketController {

    @Autowired
    private BasketService basketService;

    @ApiOperation("Add an item")
    @PostMapping
    public Basket add(@RequestBody @Valid BasketCreateRequest request) {
        return basketService.create(request);
    }

    @ApiOperation("Find all items")
    @GetMapping
    public List<Basket> findAll() { return basketService.findAll(); }

    @ApiOperation("Find an item by product id")
    @GetMapping("/productId/{productId}")
    public List<Basket> findByProductId(@PathVariable @RequestBody() String productId) {
        return basketService.findByProductId(productId); }

    @ApiOperation("Update an item quantity")
    @PatchMapping("/update/{id}")
    public Basket update(@PathVariable Long id, @RequestBody @Valid BasketUpdateRequest request) {
        return basketService.update(id, request); }

    @ApiOperation("Delete an item")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        basketService.delete(id);
    }

}
