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
    private val itemPositionOffset: Int
) : RecyclerView(slidingUpMenu.context) {

    init {
        setUpViews()
    }

    private fun setUpViews() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

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
