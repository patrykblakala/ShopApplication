package com.patryk.shop.service.impl

import com.patryk.shop.domain.dao.Product
import com.patryk.shop.repository.ProductRepository
import spock.lang.Specification

class ProductServiceImplSpec extends Specification {
    def productService
    def productRepository = Mock(ProductRepository)

    def setup() {
        productService = new ProductServiceImpl(productRepository)
    }

    def 'testing save method'() {
        given:
        Product product = Mock(Product)

        when:
        productService.save(product)

        then:
        1 * productRepository.save(product)
        0 * _
    }

    def 'testing getById method'() {
        given:
        def productId = 1
        def product = Mock(Product)

        when:
        productService.getById(productId)

        then:
        1 * productRepository.getById(productId) >> product
        0 * _
    }

    def 'testing update method'() {
        given:
        def product = new Product(1L, "productName", 10.0, 5)
        def productDb = new Product(2L, "productDbName", 15.0, 7)

        when:
        productService.update(product, 2L)

        then:
        1 * productRepository.getById(2L) >> productDb
        1 * productRepository.save(productDb)
        0 * _
    }

    def 'testing deleteById method'() {
        given:
        def productId = 1

        when:
        productService.deleteById(productId)

        then:
        1 * productRepository.deleteById(productId)
        0 * _
    }
}
