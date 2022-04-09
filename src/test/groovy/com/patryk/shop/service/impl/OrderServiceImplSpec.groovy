package com.patryk.shop.service.impl


import com.patryk.shop.domain.dao.Product
import com.patryk.shop.domain.dao.User
import com.patryk.shop.repository.OrderRepository
import com.patryk.shop.service.BasketService
import com.patryk.shop.service.ProductService
import com.patryk.shop.service.UserService
import spock.lang.Specification

class OrderServiceImplSpec extends Specification{

    def orderService
    def orderRepository = Mock(OrderRepository)
    def userService = Mock(UserService)
    def basketService = Mock(BasketService)
    def productService = Mock(ProductService)

    def setup() {
        orderService = new OrderServiceImpl(orderRepository, userService,
                basketService, productService)
    }

    def 'testing save method'() {
        given:
        def user = Mock(User)
        def productsInBasket = [new Product(id: 1,quantity: 5, price: 10.0), new Product(id: 2, quantity: 7, price: 8.0)]

        when:
        orderService.save()

        then:
        1 * userService.getCurrentUser() >> user
        1 * basketService.getProductsInBasket() >> productsInBasket
        1 * productService.getById(1)
        1 * productService.getById(2)
        1 * orderRepository.saveAll(_)
        1 * basketService.clearBasket()
        0 * _
    }

    def 'testing getByOrderNumber method'() {
        given:
        def orderNumber = "5"

        when:
        orderService.getByOrderNumber(orderNumber)

        then:
        1 * orderRepository.findByOrderNumber(orderNumber)
        0 * _
    }

    def 'testing deleteByOrderNumber method'() {
        given:
        def orderNumber = "5"

        when:
        orderService.deleteByOrderNumber(orderNumber)

        then:
        1 * orderRepository.deleteByOrderNumber(orderNumber)
        0 * _
    }

    def 'testing getTotalOrderPrice'() {
        given:
        def productsInBasket = [new Product(1L, "product1", 12.0, 5), new Product(1L, "product1", 12.0, 5)]

        when:
        orderService.getTotalOrderPrice()

        then:
        1 * basketService.getProductsInBasket() >> productsInBasket
        0 * _
    }

    def 'testing getOrderByOrderNumber method'() {
        given:
        def orderNumber = "orderNumber123"

        when: orderService.getOrderByOrderNumber(orderNumber)

        then:
        1 * orderRepository.findByOrderNumber(orderNumber)
        0 * _
    }
}
