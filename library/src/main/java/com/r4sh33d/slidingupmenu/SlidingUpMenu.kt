package com.r4sh33d.slidingupmenu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.MenuRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

class SlidingUpMenu(private val windowContext: Context, @MenuRes private val menuResource: Int) :
    BottomSheetDialog(windowContext) {

    private val recyclerView: RecyclerView

    init {
        val dialogRootView =
            LayoutInflater.from(windowContext).inflate(R.layout.dialog_root_view, null)
        recyclerView = dialogRootView.findViewById(R.id.recyclerView)
        setUpRecyclerView()
        setContentView(dialogRootView)
    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager =
            GridLayoutManager(windowContext, 4, GridLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(4, windowContext.dpToPx(16)))
        recyclerView.adapter = GridItemAdapter {
            Log.d("SlidingUpMenu", "Menu item clicked: ${it.title}")
        }.apply { submitList(windowContext.getMenuList(menuResource)) }
    }
}
