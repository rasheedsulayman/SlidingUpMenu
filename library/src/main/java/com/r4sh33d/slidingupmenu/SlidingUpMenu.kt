package com.r4sh33d.slidingupmenu

import android.content.Context
import android.icu.text.CaseMap
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

class SlidingUpMenu(
    private val windowContext: Context,
    private var title: String,
    @MenuRes private val menuResource: Int
) :
    BottomSheetDialog(windowContext) {

    private val recyclerView: RecyclerView
    private val titleTextView: TextView

    init {
        val dialogRootView =
            LayoutInflater.from(windowContext).inflate(R.layout.dialog_root_view, null)
        recyclerView = dialogRootView.findViewById(R.id.recyclerView)
        titleTextView = dialogRootView.findViewById(R.id.dialogTitleTextView)
        setUpViews()
        setContentView(dialogRootView)
    }

    private fun setUpViews() {
        titleTextView.text = title
        recyclerView.layoutManager = GridLayoutManager(windowContext, 4)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(4, windowContext.dpToPx(4)))
        recyclerView.adapter = GridItemAdapter {
            Log.d("SlidingUpMenu", "Menu item clicked: ${it.title}")
        }.apply { submitList(windowContext.getMenuList(menuResource)) }
    }
}
