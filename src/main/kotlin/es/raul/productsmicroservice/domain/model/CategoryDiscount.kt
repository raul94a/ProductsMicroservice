package es.raul.productsmicroservice.domain.model

import es.raul.productsmicroservice.utils.BigDecimalUtils
import java.math.BigDecimal


typealias DiscountedPrice = BigDecimal
typealias DiscountPercent = Double

object CategoryDiscount {
    private const val ELECTRONICS_CATEGORY = "Electronics"
    private const val HOME_AND_KITCHEN_CATEGORY = "Home & Kitchen"
    private const val ELECTRONICS_DISCOUNT = 0.15
    private const val HOME_KITCHEN_DISCOUNT = 0.25
    private const val SPECIAL_SKU_DISCOUNT = 0.30

    fun getDiscount(product: Product): Pair<DiscountedPrice, DiscountPercent> {

        if (product.sku.endsWith("5")){
            val discount = BigDecimal(1 - SPECIAL_SKU_DISCOUNT)
            val discountedPrice = BigDecimalUtils.multiplyWithRounding(product.price,discount)
            return Pair(discountedPrice, SPECIAL_SKU_DISCOUNT * 100)
        }

        if (product.category == HOME_AND_KITCHEN_CATEGORY){
            val discount = BigDecimal(1 - HOME_KITCHEN_DISCOUNT)
            val discountedPrice = BigDecimalUtils.multiplyWithRounding(product.price,discount)
            return Pair(discountedPrice, HOME_KITCHEN_DISCOUNT * 100)

        }
        if (product.category == ELECTRONICS_CATEGORY){
            val discount = BigDecimal(1 - ELECTRONICS_DISCOUNT)
            val discountedPrice = BigDecimalUtils.multiplyWithRounding(product.price,discount)
            return Pair(discountedPrice, ELECTRONICS_DISCOUNT * 100)

        }
        return Pair(BigDecimalUtils.multiplyWithRounding(product.price,BigDecimal(1)),0.0)
    }


}