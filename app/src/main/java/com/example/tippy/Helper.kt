package com.example.tippy

import java.util.Currency
import java.util.Locale

class Utils {
    private fun to2dp(amount:Double) = "%.2f".format(amount)
    fun percentString(num: Int) = "$num%"
    fun calcPercentage(num1: Double, num2: Int) = num1 * num2 / 100
    fun formatCurrency(amount: Double) = run {
        val currency: Currency = Currency.getInstance(Locale.UK)
        val symbol: String = currency.symbol
        symbol.plus(to2dp(amount))
    }

}
