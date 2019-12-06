package com.r4sh33d.slidingupmenu.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.r4sh33d.slidingupmenu.adapters.GridItemAdapter
import com.r4sh33d.slidingupmenu.utils.GridSpacingItemDecoration
import com.r4sh33d.slidingupmenu.extensions.dpToPx

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
        addItemDecoration(
            GridSpacingItemDecoration(
                4,
                context.dpToPx(4)
            )
        )
        adapter = GridItemAdapter {
            Toast.makeText(context, "Item clicked with name: ${it.title}", Toast.LENGTH_LONG).show()
        }.apply { submitList(menuItems) }
    }
}