package com.r4sh33d.slidingupmenu.utils

import android.graphics.drawable.Drawable
import android.view.MenuItem

class MenuModel(val id: Int, val title: String, val iconDrawable: Drawable?) {

    private var menuItem: MenuItem? = null

    internal constructor (menuItem: MenuItem) : this(
        menuItem.itemId,
        menuItem.title.toString(),
        menuItem.icon
    ) {
        this.menuItem = menuItem
    }
}