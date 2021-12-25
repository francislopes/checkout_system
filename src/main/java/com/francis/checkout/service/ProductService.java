package com.francis.checkout.service;

import com.francis.checkout.dto.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    public List<Product> findAll() {

        var url = "http://localhost:8081/products"; // GET
        var template = new RestTemplate();
        ResponseEntity<Product[]> response = template.getForEntity(url, Product[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public Product findById(String id) {

        var url = "http://localhost:8081/products/" + id; // GET
        var template = new RestTemplate();
        ResponseEntity<Product> response = template.getForEntity(url, Product.class);
        return response.getBody();
    }
}
