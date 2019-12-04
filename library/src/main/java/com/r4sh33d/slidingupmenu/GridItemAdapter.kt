package com.r4sh33d.slidingupmenu

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class GridItemAdapter(val menuItemClickListener: (MenuItem) -> Unit) :
    ListAdapter<MenuItem, GridItemAdapter.GridItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        return GridItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.menu_item_grid_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MenuItem>() {

        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean =
            oldItem.itemId == newItem.itemId
    }

    inner class GridItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView = itemView.findViewById<WidthFitSquareImageView>(R.id.iconImageView)
        private val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)

        init {
            itemView.setOnClickListener {
                menuItemClickListener(getItem(adapterPosition))
            }
        }

        fun bind(menuItem: MenuItem) {
            titleTextView.text = menuItem.title
            if (menuItem.icon != null) {
                iconImageView.setImageDrawable(menuItem.icon)
            } else Log.d("TAG", "Menu icon is null")
        }
    }
}
