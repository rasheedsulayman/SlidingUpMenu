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
package com.r4sh33d.slidingupmenu.adapters

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.r4sh33d.slidingupmenu.SlidingUpMenu
import com.r4sh33d.slidingupmenu.utils.MenuModel
import com.r4sh33d.slidingupmenu.utils.horizontalScrollChunkSize
import com.r4sh33d.slidingupmenu.views.MenuRecyclerView

internal class ViewPagerAdapter(
    private val slidingUpMenu: SlidingUpMenu,
    private val groupedMenuItemsList: List<List<MenuModel>>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = groupedMenuItemsList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val recyclerView = MenuRecyclerView(
            groupedMenuItemsList[position],
            slidingUpMenu,
            position * horizontalScrollChunkSize
        )
        container.addView(recyclerView)
        return recyclerView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as MenuRecyclerView)
    }
}
