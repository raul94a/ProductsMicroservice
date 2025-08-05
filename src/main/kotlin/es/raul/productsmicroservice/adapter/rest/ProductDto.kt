package es.raul.productsmicroservice.adapter.rest

import java.math.BigDecimal

data class ProductDto(
    val sku: String,
    val description: String,
    val category: String,
    val originalPrice: BigDecimal,
    val discountedPrice: BigDecimal,
    val discountPercentage: Double
)
