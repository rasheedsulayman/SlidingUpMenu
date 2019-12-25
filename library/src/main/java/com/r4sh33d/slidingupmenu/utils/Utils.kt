package com.r4sh33d.slidingupmenu.utils

//Todo calculate chunk size based on available space.
internal const val horizontalScrollChunkSize = 8

internal fun splitMenuList(
    list: MutableList<MenuModel>,
    scrollDirection: ScrollDirection
): List<List<MenuModel>> =
    list.chunked(if (scrollDirection == ScrollDirection.HORIZONTAL) horizontalScrollChunkSize else list.size)

internal fun getTopLeftCornerRadius(cornerRadius: Float): FloatArray = floatArrayOf(
    cornerRadius, cornerRadius, cornerRadius, cornerRadius,
    0f, 0f, 0f, 0f
)