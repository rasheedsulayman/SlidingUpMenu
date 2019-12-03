package com.r4sh33d.slidingupmenu

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import kotlin.math.roundToInt

fun Context.getScreenSizeDp(): SizeFloat {
    val displayMetrics: DisplayMetrics = resources.displayMetrics
    val dpHeight = displayMetrics.heightPixels / displayMetrics.density
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return SizeFloat(dpHeight, dpWidth)
}

fun Context.dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
    ).roundToInt()
}

fun Context.getScreenSizePx(): SizeInt {
    val displayMetrics = resources.displayMetrics
    return SizeInt(displayMetrics.heightPixels, displayMetrics.widthPixels)
}

class SizeFloat(val height: Float, val width: Float)
class SizeInt(val height: Int, val width: Int)