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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.r4sh33d.slidingupmenu.R
import com.r4sh33d.slidingupmenu.SlidingUpMenu
import com.r4sh33d.slidingupmenu.extensions.show
import com.r4sh33d.slidingupmenu.utils.MenuModel
import com.r4sh33d.slidingupmenu.utils.MenuType.GRID
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.maybeSetTextColor
import com.r4sh33d.slidingupmenu.utils.getItemSelector
import com.r4sh33d.slidingupmenu.views.WidthFitSquareImageView

internal class MenuModelAdapter(
    private val slidingUpMenu: SlidingUpMenu,
    private val itemPositionOffset: Int
) : ListAdapter<MenuModel, MenuModelAdapter.GridItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        return GridItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                if (slidingUpMenu.menuType == GRID) R.layout.menu_item_grid_layout else R.layout.menu_list_item_layout,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MenuModel>() {

        override fun areItemsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean =
            oldItem.id == newItem.id
    }

    inner class GridItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView =
            itemView.findViewById<WidthFitSquareImageView>(R.id.iconImageView)
        private val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)

        init {
            itemView.background = slidingUpMenu.getItemSelector()
            titleTextView.maybeSetTextColor(slidingUpMenu.context, R.attr.sm_body_text_color)
            slidingUpMenu.bodyTextFont?.let { titleTextView.typeface = it }
            itemView.setOnClickListener {
                slidingUpMenu.menuModelSelectedListener?.invoke(
                    slidingUpMenu,
                    getItem(adapterPosition),
                    adapterPosition + itemPositionOffset
                )
                if (slidingUpMenu.dismissMenuOnItemSelected) slidingUpMenu.dismiss()
            }
        }

        fun bind(menuModel: MenuModel) {
            titleTextView.text = menuModel.title
            if (menuModel.iconDrawable != null) {
                iconImageView.show()
                iconImageView.setImageDrawable(menuModel.iconDrawable)
            }
        }
    }
}
