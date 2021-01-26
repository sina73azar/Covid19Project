package com.sina.covid19project.utils_extentions

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.math.RoundingMode
import java.text.SimpleDateFormat

fun Long.toStringSplit3By3():String{
    return ""
}
fun Int.toStringSplit3By3():String{
    return ""
}
fun Float.round(i: Int): Float {
    return toBigDecimal().setScale(i, RoundingMode.UP).toFloat()
}
@SuppressLint("SimpleDateFormat")
fun Long.reformat(): String {
    val df = SimpleDateFormat("(yyyy/MM/dd - HH:mm:ss)")
    return df.format(this)
}
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}
//
//fun TextView.setAfterText(str:String){
//    this.text=this.text+str
//
//}