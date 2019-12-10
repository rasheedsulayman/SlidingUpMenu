package com.r4sh33d.slidingupmenu.utils

import android.graphics.Typeface
import androidx.annotation.ColorInt
import com.r4sh33d.slidingupmenu.SlidingUpMenu

internal class SizeFloat(val height: Float, val width: Float)
internal class SizeInt(val height: Int, val width: Int)

enum class MenuType {
    LIST, GRID
}

enum class ScrollDirection {
    HORIZONTAL, VERTICAL
}

internal class BodyTextStyle(@ColorInt var textColor: Int? = null, var font: Typeface? = null)

typealias MenuModelSelectedListener = (slidingUpMenu: SlidingUpMenu, menuModel: MenuModel, position: Int) -> Unit
