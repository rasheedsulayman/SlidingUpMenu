package com.r4sh33d.slidingupmenu.views

import android.annotation.SuppressLint
import android.content.Context
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.r4sh33d.slidingupmenu.utils.ScrollDirection

@SuppressLint("ViewConstructor")
class WrapContentViewPager(context: Context, private val scrollDirection: ScrollDirection) :
    ViewPager(context) {

    init {
        layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (scrollDirection == ScrollDirection.VERTICAL) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        var height = 0
        var newHeightMeasureSpec = heightMeasureSpec
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) height = h
        }
        if (height != 0) newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }
}

