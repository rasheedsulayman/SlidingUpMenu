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
package com.r4sh33d.slidingupmenu.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount
        if (position < spanCount) outRect.top = spacing
        outRect.bottom = spacing
    }
}
