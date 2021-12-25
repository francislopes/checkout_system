package com.francis.checkout.controller;

import com.francis.checkout.dto.Product;
import com.francis.checkout.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Product Resource")
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @ApiOperation("Find all products")
    @GetMapping
    public List<Product> findAll() {
        return service.findAll();
    }

    @ApiOperation("Find a product by id")
    @GetMapping("/id/{id}")
    public Product findById(@PathVariable String id) {
        return service.findById(id);
    }
}
