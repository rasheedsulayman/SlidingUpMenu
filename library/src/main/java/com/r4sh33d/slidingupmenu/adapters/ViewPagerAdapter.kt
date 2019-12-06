package com.r4sh33d.slidingupmenu.adapters

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.r4sh33d.slidingupmenu.views.MenuRecyclerView

class ViewPagerAdapter(
    private val context: Context,
    private val groupedMenuItemsList: List<List<MenuItem>>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = groupedMenuItemsList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val recyclerView = MenuRecyclerView(
            context,
            groupedMenuItemsList[position]
        )
        container.addView(recyclerView)
        return recyclerView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as MenuRecyclerView)
    }
}