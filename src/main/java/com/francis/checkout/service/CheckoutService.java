package com.francis.checkout.service;

import com.francis.checkout.dto.CheckoutProduct;
import com.francis.checkout.dto.CheckoutTotal;
import com.francis.checkout.dto.Promotion;
import com.francis.checkout.enums.PromotionType;
import com.francis.checkout.model.Basket;
import com.francis.checkout.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CheckoutService {

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private ProductService productService;


    public CheckoutTotal payAndClear() {
        var products = basketRepository.findAll();
        CheckoutTotal checkout = calculate(products);
        System.out.println("PAID!");
        basketRepository.deleteAll();
        return checkout;
    }

    /**
     * The following methods are used to to apply the discounts and to calculate the totals
     * to be showed to the customer.
     */

    public CheckoutTotal calculate(List<Basket> products) {

        CheckoutTotal checkout = new CheckoutTotal();

        Double rawTotal = (double) 0;
        HashMap<String, CheckoutProduct> mapProducts = new HashMap<>();

        for (Basket product : products) {
            rawTotal = rawTotal + (product.getPrice() * product.getQuantity());

            if (mapProducts.containsKey(product.getProductId())) {
                var item = mapProducts.get(product.getProductId());
                item.setQuantity(item.getQuantity() + product.getQuantity());
                item.setPrice(item.getPrice() + (product.getPrice() * product.getQuantity()));
            } else {
                CheckoutProduct checkoutProduct = new CheckoutProduct();
                checkoutProduct.setProductId(product.getProductId());
                checkoutProduct.setName(product.getProductName());
                checkoutProduct.setQuantity(product.getQuantity());
                checkoutProduct.setPrice((product.getPrice() * product.getQuantity()));

                mapProducts.put(product.getProductId(), checkoutProduct);
            }
        }
        mapProducts.values().forEach(f -> checkout.getProducts().add(f));
        checkout.setRawTotal(rawTotal);

        for (CheckoutProduct p : checkout.getProducts()) {
            qtyBasedPriceOverride(checkout, p);
            buyXGetYFree(checkout, p);
            flatPercent(checkout, p);
        }

        var discount = checkout.getProducts().stream()
                .filter(f -> f.getDiscount() != null)
                .map(CheckoutProduct::getDiscount)
                .reduce(0.0, Double::sum);

        checkout.setTotalPromotions(discount);
        checkout.setTotalPayable(checkout.getRawTotal() - checkout.getTotalPromotions());

        return checkout;
    }


    // QTY_BASED_PRICE_OVERRIDE
    private void qtyBasedPriceOverride(CheckoutTotal checkout, CheckoutProduct checkoutProduct) {
        var product = productService.findById(checkoutProduct.getProductId());

        Double priceWithPromotion = (double) 0;

        for (Promotion promotion : product.getPromotions()) {
            if (promotion.getType().equals(PromotionType.QTY_BASED_PRICE_OVERRIDE.name())) {

                var quantity = checkoutProduct.getQuantity();

                if (quantity == 1) {
                    checkoutProduct.setPriceWithPromotion(priceWithPromotion);
                } else if (quantity % 2 == 0) {
                    checkoutProduct.setPriceWithPromotion(priceWithPromotion + (quantity / 2) * promotion.getPrice());
                } else {
                    var valueLessOne = quantity - 1;
                    checkoutProduct.setPriceWithPromotion(((valueLessOne / 2) * promotion.getPrice()) + product.getPrice());
                }

                if (checkoutProduct.getPriceWithPromotion() > new Double("0.0")) {
                    checkoutProduct.setDiscount(checkoutProduct.getDiscount() + checkoutProduct.getPrice() - checkoutProduct.getPriceWithPromotion());
                }

                checkout.setTotalPromoQtyBasedPriceOverride(checkout.getTotalPromoQtyBasedPriceOverride() + checkoutProduct.getDiscount());
            }
        }
    }

    // BUY_X_GET_Y_FREE
    private void buyXGetYFree(CheckoutTotal checkout, CheckoutProduct checkoutProduct) {
        var product = productService.findById(checkoutProduct.getProductId());

        Double priceWithPromotion = (double) 0;

        for (Promotion promotion : product.getPromotions()) {
            if (promotion.getType().equals(PromotionType.BUY_X_GET_Y_FREE.name())) {

                var quantity = checkoutProduct.getQuantity();

                if (quantity <= promotion.getRequiredQty()) {
                    checkoutProduct.setPriceWithPromotion(priceWithPromotion);
                } else if (quantity % 3 == 0) {
                    checkoutProduct.setPriceWithPromotion((quantity - quantity / 3) * product.getPrice());
                } else if ((quantity - 1) % 3 == 0) {
                    var valueLessOne = quantity - 1;
                    checkoutProduct.setPriceWithPromotion((quantity - (valueLessOne / 3)) * product.getPrice());
                } else if ((quantity - 2) % 3 == 0) {
                    var valueLessTwo = quantity - 2;
                    checkoutProduct.setPriceWithPromotion((quantity - (valueLessTwo / 3)) * product.getPrice());
                }

                if (checkoutProduct.getPriceWithPromotion() > new Double("0.0")) {
                    checkoutProduct.setDiscount(checkoutProduct.getDiscount() + checkoutProduct.getPrice() - checkoutProduct.getPriceWithPromotion());
                }

                checkout.setTotalPromoByXGetYFree(checkout.getTotalPromoByXGetYFree() + checkoutProduct.getDiscount());
            }
        }
    }

    // FLAT_PERCENT
    private void flatPercent(CheckoutTotal checkout, CheckoutProduct checkoutProduct) {
        var product = productService.findById(checkoutProduct.getProductId());

        var percentage = new Double("0.0");
        for (Promotion promotion : product.getPromotions()) {
            if (promotion.getType().equals(PromotionType.FLAT_PERCENT.name())) {

                percentage = (product.getPrice() * promotion.getAmount()) / 100;
                checkoutProduct.setPriceWithPromotion(checkoutProduct.getPrice() - percentage);

                if (checkoutProduct.getPriceWithPromotion() > new Double("0.0")) {
                    checkoutProduct.setDiscount(checkoutProduct.getDiscount() + percentage);
                }

                checkout.setTotalPromoFlatPercent(checkout.getTotalPromoFlatPercent() + checkoutProduct.getDiscount());
            }
        }
    }
}
