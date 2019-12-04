package com.r4sh33d.slidingupmenu

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.MenuRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

class SlidingUpMenu(private val windowContext: Context, @MenuRes menuResource: Int) :
    BottomSheetDialog(windowContext) {

    init {
        val dialogRootView = LayoutInflater.from(windowContext).inflate(R.layout.dialog_root_view, null)
        val recyclerView = dialogRootView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(windowContext, 4, GridLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(4, windowContext.dpToPx(16)))
        setContentView(dialogRootView)
    }

}