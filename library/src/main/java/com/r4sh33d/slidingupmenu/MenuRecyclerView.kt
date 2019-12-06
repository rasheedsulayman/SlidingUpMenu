package com.r4sh33d.slidingupmenu

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("ViewConstructor")
class MenuRecyclerView(
    context: Context,
    private val menuItems: List<MenuItem>
) : RecyclerView(context) {

    init {
        setUpViews()
    }

    private fun setUpViews() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        layoutManager = object : GridLayoutManager(context, 4) {
            override fun canScrollHorizontally(): Boolean = false
            override fun canScrollVertically(): Boolean = false
        }

        addItemDecoration(GridSpacingItemDecoration(4, context.dpToPx(4)))
        adapter = GridItemAdapter {
            Log.d("SlidingUpMenu", "Menu item clicked: ${it.title}")
        }.apply { submitList(menuItems) }
    }
}