package com.r4sh33d.slidingupmenu.utils

import android.view.MenuItem

internal data class SizeFloat(val height: Float, val width: Float)
internal data class SizeInt(val height: Int, val width: Int)

internal fun splitMenuList(list: ArrayList<MenuItem>): List<List<MenuItem>> = list.chunked(8)