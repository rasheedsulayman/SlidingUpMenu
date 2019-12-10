package com.r4sh33d.slidingupmenu.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import com.r4sh33d.slidingupmenu.utils.MenuModel
import com.r4sh33d.slidingupmenu.utils.SizeFloat
import com.r4sh33d.slidingupmenu.utils.SizeInt
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

internal fun Context.getMenuList(@MenuRes menuRes: Int): ArrayList<MenuModel> {
    return PopupMenu(this, null).menu.run {
        MenuInflater(this@getMenuList).inflate(menuRes, this)
        val list = ArrayList<MenuModel>()
        for (index in 0 until size()) {
            list.add(MenuModel(getItem(index)))
        }
        list
    }
}

internal fun Context.getScreenSizePx(): SizeInt {
    val displayMetrics = resources.displayMetrics
    return SizeInt(
        displayMetrics.heightPixels,
        displayMetrics.widthPixels
    )
}

@SuppressLint("InlinedApi")
internal fun Context.getThemeBackgroundColor(): Int {
    val attributes = theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorBackgroundFloating))
    try {
        return attributes.getColor(0, Color.WHITE)
    } finally {
        attributes.recycle()
    }
}
