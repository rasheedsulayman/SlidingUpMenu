package com.r4sh33d.slidingupmenu.views

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.r4sh33d.slidingupmenu.adapters.MenuModelAdapter
import com.r4sh33d.slidingupmenu.extensions.dpToPx
import com.r4sh33d.slidingupmenu.utils.*
import com.r4sh33d.slidingupmenu.utils.BodyTextStyle

@SuppressLint("ViewConstructor")
internal class MenuRecyclerView(
    context: Context,
    private val menuItems: List<MenuModel>,
    private val menuType: MenuType,
    private val scrollDirection: ScrollDirection,
    private val bodyTextStyle: BodyTextStyle
) : RecyclerView(context) {

    init {
        setUpViews()
    }

    private fun setUpViews() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        layoutManager = when (menuType) {
            MenuType.LIST -> LinearLayoutManager(context)
            MenuType.GRID -> object : GridLayoutManager(context, 4) {
                override fun canScrollHorizontally(): Boolean = false
                override fun canScrollVertically(): Boolean =
                    scrollDirection == ScrollDirection.VERTICAL
            }
        }

        addItemDecoration(
            GridSpacingItemDecoration(
                4,
                context.dpToPx(4)
            )
        )

        adapter = MenuModelAdapter(menuType, bodyTextStyle) {
            Toast.makeText(context, "Item clicked with name: ${it.title}", Toast.LENGTH_LONG).show()
        }.apply { submitList(menuItems) }
    }
}
