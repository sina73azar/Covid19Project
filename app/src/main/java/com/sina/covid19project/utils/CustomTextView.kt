package com.sina.covid19project.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


import androidx.annotation.Nullable
import com.sina.covid19project.R


class CustomTextView(context: Context, @Nullable attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {
    private val mTextAfter: String?

    @SuppressLint("SetTextI18n")
    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(text.toString() + if (TextUtils.isEmpty(mTextAfter)) "" else mTextAfter, type)
    }

    init {
        val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        mTextAfter = array.getString(R.styleable.CustomTextView_textAfter)
        array.recycle()
    }
}