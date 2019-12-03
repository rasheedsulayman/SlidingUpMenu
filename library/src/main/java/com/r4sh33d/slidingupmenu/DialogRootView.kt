package com.r4sh33d.slidingupmenu

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class DialogRootView(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
            context.getScreenSizePx().width,
            MeasureSpec.getMode(widthMeasureSpec)
        )
        super.onMeasure(newWidthMeasureSpec, heightMeasureSpec)
    }
}