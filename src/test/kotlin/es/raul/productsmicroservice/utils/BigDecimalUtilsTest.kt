package es.raul.productsmicroservice.utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.math.RoundingMode

class BigDecimalUtilsTest{
    @Test
    fun multiplyWithRounding(){
        val bigDecimal =  BigDecimal(10.0)
        val bigDecimalMultiplier = BigDecimal(2.0)
        val decimals = 3
        val result = BigDecimalUtils.multiplyWithRounding(bigDecimal,bigDecimalMultiplier, decimals = decimals)
        val expectedResult = BigDecimal(20.0).setScale(decimals,RoundingMode.HALF_UP)
        assertEquals(expectedResult,result)
    }
}