package es.raul.productsmicroservice.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal


@Table("product")
data class Product(
    @Id val sku: String,
    val price: BigDecimal,
    val description: String,
    val category: String
)