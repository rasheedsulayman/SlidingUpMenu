package com.r4sh33d.slidingupmenu

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import kotlin.math.roundToInt

internal fun Context.getScreenSizeDp(): SizeFloat {
    val displayMetrics: DisplayMetrics = resources.displayMetrics
    val dpHeight = displayMetrics.heightPixels / displayMetrics.density
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return SizeFloat(dpHeight, dpWidth)
}

internal fun Context.dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
    ).roundToInt()
}

fun Context.getMenuList(@MenuRes menuRes: Int): ArrayList<MenuItem> {
    return PopupMenu(this, null).menu.run {
        MenuInflater(this@getMenuList).inflate(menuRes, this)
        val list = ArrayList<MenuItem>()
        for (index in 0 until size()) {
            list.add(getItem(index))
        }
        list
    }
}

fun Context.getScreenSizePx(): SizeInt {
    val displayMetrics = resources.displayMetrics
    return SizeInt(displayMetrics.heightPixels, displayMetrics.widthPixels)
}