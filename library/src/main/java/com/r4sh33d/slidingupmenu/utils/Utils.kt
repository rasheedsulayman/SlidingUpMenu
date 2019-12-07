package com.r4sh33d.slidingupmenu.utils

import android.view.MenuItem

internal data class SizeFloat(val height: Float, val width: Float)
internal data class SizeInt(val height: Int, val width: Int)

enum class MenuType {
    LIST, GRID
}

enum class ScrollDirection {
    HORIZONTAL, VERTICAL
}

internal fun splitMenuList(list: ArrayList<MenuItem>): List<List<MenuItem>> = list.chunked(8)