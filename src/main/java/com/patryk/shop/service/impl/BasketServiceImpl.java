package com.patryk.shop.service.impl;

import com.patryk.shop.domain.dao.Basket;
import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.domain.dao.User;
import com.patryk.shop.exception.QuantityExceededException;
import com.patryk.shop.repository.BasketRepository;
import com.patryk.shop.service.BasketService;
import com.patryk.shop.service.ProductService;
import com.patryk.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ProductService productService;


    @Override
    public void addToBasket(Long productId, Integer quantity) {
        User currentUser = userService.getCurrentUser();
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndProductId(currentUser.getId(), productId);
        basketOptional.ifPresentOrElse(basket -> {

            Product product = basket.getProduct();
            if (product.getQuantity() < basket.getQuantity() + quantity) {
                throw new QuantityExceededException("there is not enough quantity for product: " + product.getId());
            }
            basket.setQuantity(basket.getQuantity() + quantity);
            basketRepository.save(basket);
        }, () -> {
            Product productDb = productService.getById(productId);
            if (quantity <= productDb.getQuantity()) {
                Basket basket = Basket.builder()
                        .user(currentUser)
                        .product(productDb)
                        .quantity(quantity)
                        .build();
                basketRepository.save(basket);
            }
        });
    }


    @Override
    public void clearBasket() {
        Long userId = userService.getCurrentUser().getId();
        basketRepository.deleteByUserId(userId);
    }

    @Override
    public List<Product> getProductsInBasket() {
        User currentUser = userService.getCurrentUser();
        return basketRepository.findByUserId(currentUser.getId()).stream()
                .map(basket -> {
                    Product product = basket.getProduct();
                    product.setQuantity(basket.getQuantity());
                    return product;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {
        Long userId = userService.getCurrentUser().getId();
        basketRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
