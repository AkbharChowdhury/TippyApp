package com.example.tippy



fun to2dp(amount: Double): String {
    return "%.2f".format(amount)
}

fun calcPercentage(num1: Double, num2: Int): Double {
    return (num1 * num2) / 100
}