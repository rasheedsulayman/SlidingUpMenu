package com.r4sh33d.slidingupmenu.utils

internal const val horizontalScrollChunkSize = 8
internal fun splitMenuList(
    list: MutableList<MenuModel>,
    scrollDirection: ScrollDirection
): List<List<MenuModel>> =
    list.chunked(if (scrollDirection == ScrollDirection.HORIZONTAL) horizontalScrollChunkSize else list.size)


