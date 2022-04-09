package com.patryk.shop.service.impl

import com.patryk.shop.domain.dao.Basket
import com.patryk.shop.domain.dao.Product
import com.patryk.shop.domain.dao.User
import com.patryk.shop.exception.QuantityExceededException
import com.patryk.shop.repository.BasketRepository
import com.patryk.shop.service.ProductService
import com.patryk.shop.service.UserService
import spock.lang.Specification

class BasketServiceImplSpec extends Specification {

    def basketService
    def basketRepository = Mock(BasketRepository)
    def userService = Mock(UserService)
    def productService = Mock(ProductService)

    def setup() {
        basketService = new BasketServiceImpl(basketRepository, userService, productService)
    }

    def 'testing addToBasket method should add to basket'() {
        given:
        def productId = 1
        def quantity = 5
        def product = Mock(Product)
        def basket = Mock(Basket)
        def user = Mock(User)

        when:
        basketService.addToBasket(productId, quantity)

        then:
        1 * user.getId() >> 1
        1 * userService.getCurrentUser() >> user
        1 * basketRepository.findByUserIdAndProductId(1, productId) >> Optional.of(basket)
        1 * basket.getProduct() >> product
        1 * product.getQuantity() >> 10
        2 * basket.getQuantity() >> 5
        1 * basket.setQuantity(_)
        1 * basketRepository.save(basket)
        0 * _
    }

    def 'testing addToBasket method should throw an exception'() {
        given:
        def productId = 1
        def quantity = 5
        def product = Mock(Product)
        def basket = Mock(Basket)
        def user = Mock(User)
        productService.getById(1) >> product
        product.getQuantity() >> 3
        userService.getCurrentUser() >> user
        user.getId() >> 1
        basketRepository.findByUserIdAndProductId(1, productId) >> Optional.of(basket)
        basket.getProduct() >> product
        product.getQuantity() >> 10
        basket.getQuantity() >> 5

        when:
        basketService.addToBasket(productId, quantity)

        then:

        thrown QuantityExceededException
    }

    def 'testing addToBasket method should add to basket if basket is empty'() {
        given:
        def productId = 1
        def quantity = 5
        def product = Mock(Product)
        def user = Mock(User)

        when:
        basketService.addToBasket(productId, quantity)

        then:
        1 * user.getId() >> 1
        1 * userService.getCurrentUser() >> user
        1 * basketRepository.findByUserIdAndProductId(1, productId) >> Optional.empty()
        1 * productService.getById(1) >> product
        1 * product.getQuantity() >> 10
        1 * basketRepository.save(_ as Basket)
        0 * _
    }

    def 'testing clearBasket method'() {
        given:
        Long userId = 1
        User user = Mock(User)

        when:
        basketService.clearBasket()

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.deleteByUserId(1)
        0 * _
    }

    def'testing getProductsInBasket method'() {
        given:
        User user = Mock(User)
        Product product = Mock(Product)
        List<Basket> baskets = [new Basket(product: product, quantity: 3),
                                new Basket(product:  product, quantity:  5)]

        when:
        basketService.getProductsInBasket()

        then:
        1 * userService.getCurrentUser() >> user
        1 * basketRepository.findByUserId(user.getId()) >> baskets
    }

    def 'testing deleteProduct method' () {
        given:
        def productId = 1
        def product = Mock(Product)
        def user = Mock(User)

        when:
        basketService.deleteProduct(productId)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.getId() >> 1
        1 * basketRepository.deleteByUserIdAndProductId(1, 1)
        0 * _

    }
}
