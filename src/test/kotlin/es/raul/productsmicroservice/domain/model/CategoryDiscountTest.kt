package es.raul.productsmicroservice.domain.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.math.RoundingMode

class CategoryDiscountTest {
    @Test
    fun getDiscount_NoDiscount() {
        val product = Product(
            sku = "SKU0012",
            category = "Stationery",
            description = "Lorem Ipsum",
            price = BigDecimal(10.0)
        )

        val (discount,percent) = CategoryDiscount.getDiscount(product)
        val expectedDiscountPrice = BigDecimal(10.0).setScale(2,RoundingMode.HALF_UP)
        val expectedDiscountPercent = 0.0
        assertEquals(expectedDiscountPrice,discount)
        assertEquals(expectedDiscountPercent,percent)

    }
    @Test
    fun getDiscount_WithSkuEndingFive_Electronics() {
        val product = Product(
            sku = "SKU0005",
            category = "Electronics",
            description = "Lorem Ipsum",
            price = BigDecimal(10.0)
        )

        val (discount,percent) = CategoryDiscount.getDiscount(product)
        val expectedDiscountPrice = BigDecimal(7.0).setScale(2,RoundingMode.HALF_UP)
        val expectedDiscountPercent = 30.0
        assertEquals(expectedDiscountPrice,discount)
        assertEquals(expectedDiscountPercent,percent)

    }
    @Test
    fun getDiscount_WithSkuEndingFive_Home() {
        val product = Product(
            sku = "SKU0005",
            category = "Home & Kitchen",
            description = "Lorem Ipsum",
            price = BigDecimal(10.0)
        )

        val (discount,percent) = CategoryDiscount.getDiscount(product)
        val expectedDiscountPrice = BigDecimal(7.0).setScale(2,RoundingMode.HALF_UP)
        val expectedDiscountPercent = 30.0
        assertEquals(expectedDiscountPrice,discount)
        assertEquals(expectedDiscountPercent,percent)

    }
    @Test
    fun getDiscount_WithSkuNotEndingFive_Home() {
        val product = Product(
            sku = "SKU0006",
            category = "Home & Kitchen",
            description = "Lorem Ipsum",
            price = BigDecimal(10.0)
        )

        val (discount,percent) = CategoryDiscount.getDiscount(product)
        val expectedDiscountPrice = BigDecimal(7.5).setScale(2,RoundingMode.HALF_UP)
        val expectedDiscountPercent = 25.0
        assertEquals(expectedDiscountPrice,discount)
        assertEquals(expectedDiscountPercent,percent)

    }
    @Test
    fun getDiscount_WithSkuNotEndingFive_Electronics() {
        val product = Product(
            sku = "SKU0006",
            category = "Electronics",
            description = "Lorem Ipsum",
            price = BigDecimal(10.0)
        )

        val (discount,percent) = CategoryDiscount.getDiscount(product)
        val expectedDiscountPrice = BigDecimal(8.5).setScale(2,RoundingMode.HALF_UP)
        val expectedDiscountPercent = 15.0
        assertEquals(expectedDiscountPrice,discount)
        assertEquals(expectedDiscountPercent,percent)

    }
}