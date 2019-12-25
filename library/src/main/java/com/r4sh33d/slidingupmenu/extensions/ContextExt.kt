/**
 * Copyright (c) Rasheed Sulayman.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.r4sh33d.slidingupmenu.extensions

import android.content.Context
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
