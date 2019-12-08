package com.r4sh33d.slidingupmenu.utils

internal fun splitMenuList(
    list: ArrayList<MenuModel>,
    scrollDirection: ScrollDirection
): List<List<MenuModel>> = list.chunked(if (scrollDirection == ScrollDirection.HORIZONTAL) 8 else list.size)


