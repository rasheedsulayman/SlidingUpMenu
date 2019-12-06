package com.r4sh33d.slidingupmenu.views

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class WrapContentViewPager(context: Context, attrs: AttributeSet?) :
    ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
        var newHeightMeasureSpec = heightMeasureSpec
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h  = child.measuredHeight
            if (h > height) height = h
        }
        if (height != 0) newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }
}

