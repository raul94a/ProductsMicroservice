package es.raul.productsmicroservice.utils

import java.math.BigDecimal
import java.math.RoundingMode

object BigDecimalUtils {

    fun multiplyWithRounding(a:BigDecimal,b:BigDecimal, decimals:Int = 2) : BigDecimal{
       return a.multiply(b).setScale(decimals,RoundingMode.HALF_UP)
    }

}