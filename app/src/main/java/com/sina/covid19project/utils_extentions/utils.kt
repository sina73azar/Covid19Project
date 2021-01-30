package com.sina.covid19project.utils_extentions

import android.annotation.SuppressLint
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


fun Float.round(i: Int): Float {
    return toBigDecimal().setScale(i, RoundingMode.UP).toFloat()
}

@SuppressLint("SimpleDateFormat")
fun Long.reformat(): String {
    val df = SimpleDateFormat("(yyyy/MM/dd - HH:mm:ss)")
    return df.format(this)
}

fun getFormattedAmount(amount: Int): String
        =NumberFormat.getNumberInstance(Locale.US).format(amount)
