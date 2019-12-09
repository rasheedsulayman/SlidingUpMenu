package com.r4sh33d.slidingupmenu.utils

internal fun splitMenuList(
    list: MutableList<MenuModel>,
    scrollDirection: ScrollDirection
): List<List<MenuModel>> = list.chunked(if (scrollDirection == ScrollDirection.HORIZONTAL) 8 else list.size)


