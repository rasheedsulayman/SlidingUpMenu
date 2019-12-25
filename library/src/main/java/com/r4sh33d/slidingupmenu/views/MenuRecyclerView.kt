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
package com.r4sh33d.slidingupmenu.views

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.r4sh33d.slidingupmenu.SlidingUpMenu
import com.r4sh33d.slidingupmenu.adapters.MenuModelAdapter
import com.r4sh33d.slidingupmenu.extensions.dpToPx
import com.r4sh33d.slidingupmenu.utils.GridSpacingItemDecoration
import com.r4sh33d.slidingupmenu.utils.MenuModel
import com.r4sh33d.slidingupmenu.utils.MenuType
import com.r4sh33d.slidingupmenu.utils.ScrollDirection

@SuppressLint("ViewConstructor")
internal class MenuRecyclerView(
    private val menuItems: List<MenuModel>,
    private val slidingUpMenu: SlidingUpMenu,
    itemPositionOffset: Int
) : RecyclerView(slidingUpMenu.context) {

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
            if (slidingUpMenu.menuType == MenuType.GRID) {
                val padding = context.dpToPx(16)
                setPadding(padding, 0, padding, 0)
            }
        }
        layoutManager = when (slidingUpMenu.menuType) {
            MenuType.LIST -> LinearLayoutManager(context)
            MenuType.GRID -> object : GridLayoutManager(context, 4) {
                override fun canScrollHorizontally(): Boolean = false
                override fun canScrollVertically(): Boolean =
                    slidingUpMenu.scrollDirection == ScrollDirection.VERTICAL
            }
        }
        addItemDecoration(GridSpacingItemDecoration(4, context.dpToPx(4)))
        adapter = MenuModelAdapter(slidingUpMenu, itemPositionOffset).apply { submitList(menuItems) }
    }
}
