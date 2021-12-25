package com.francis.checkout.service;

import com.francis.checkout.dto.Product;
import com.francis.checkout.model.Basket;
import com.francis.checkout.model.request.BasketCreateRequest;
import com.francis.checkout.model.request.BasketUpdateRequest;
import com.francis.checkout.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    @Autowired
    public BasketService(BasketRepository basketRepository, ProductService productService) {
        this.basketRepository = basketRepository;
        this.productService = productService;
    }

    public Basket create(BasketCreateRequest request) {

        Product product = productService.findById(request.getProductId());
        if (product.getId().equals(request.getProductId())  && request.getQuantity() >= 1) {
            var item = new Basket();
            item.setProductName(product.getName());
            item.setQuantity(request.getQuantity());
            item.setPrice(product.getPrice());
            item.setProductId(product.getId());
            return  basketRepository.saveAndFlush(item);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public List<Basket> findAll() { return basketRepository.findAll(); }

    public Basket findById(Long id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found."));
    }

    public List<Basket> findByProductId(String productId) {
        List<Basket> basketList = basketRepository.findByProductId(productId);
        if (basketList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return basketList;
    }

    public Basket update(Long id, BasketUpdateRequest request) {
        if (request.getQuantity() >= 1) {
            var basket = findById(id);
            basket.setQuantity(request.getQuantity());
            return basketRepository.saveAndFlush(basket);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public void delete(Long id) {
        basketRepository.deleteById(id);
    }

}
