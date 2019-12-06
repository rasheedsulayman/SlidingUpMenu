package com.r4sh33d.slidingupmenu.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import com.r4sh33d.slidingupmenu.extensions.getScreenSizePx
import kotlin.math.min

class DialogRootView(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    val TAG = "DialogRootView"

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val screenSize =  context.getScreenSizePx()
        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
            min(screenSize.width, screenSize.height),
            MeasureSpec.getMode(widthMeasureSpec)
        )
        Log.d(TAG, "The calculated screen size, height: ${screenSize.height}, width: ${screenSize.width}")
        super.onMeasure(newWidthMeasureSpec, heightMeasureSpec)
    }
}