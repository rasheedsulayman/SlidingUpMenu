package com.r4sh33d.slidingupmenu.utils

internal data class SizeFloat(val height: Float, val width: Float)
internal data class SizeInt(val height: Int, val width: Int)

enum class MenuType {
    LIST, GRID
}

enum class ScrollDirection {
    HORIZONTAL, VERTICAL
}

internal fun splitMenuList(
    list: ArrayList<MenuModel>,
    scrollDirection: ScrollDirection
): List<List<MenuModel>> = list.chunked(if (scrollDirection == ScrollDirection.HORIZONTAL) 8 else list.size)


