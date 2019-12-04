package com.r4sh33d.slidingupmenu

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class WidthFitSquareImageView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) =
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
}