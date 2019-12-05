package com.r4sh33d.slidingupmenu

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.math.abs
import kotlin.math.min


class SlidingUpMenu(
    private val windowContext: Context,
    private var title: String,
    @MenuRes private val menuResource: Int
) : BottomSheetDialog(windowContext) {
    val TAG = "SlidingUpMenu"
    private val dialogRootView: View =
        LayoutInflater.from(windowContext).inflate(R.layout.dialog_root_view, null)
    private val recyclerView = dialogRootView.findViewById<RecyclerView>(R.id.recyclerView)
    private val titleTextView = dialogRootView.findViewById<TextView>(R.id.dialogTitleTextView)

    init {
        setUpWindowBackground()
        setUpViews()
        setContentView(dialogRootView)
        dialogRootView.onGlobalLayout {
            behavior.peekHeight = dialogRootView.height
        }
    }

    private fun setUpWindowBackground() {
        val marginLeftRight = context.getScreenSizePx().run {
            if (width == min(width, height)) 0 else ((0.8 * abs(width - height)) / 2).toInt()
        }
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), marginLeftRight, 0, marginLeftRight, 0)
        window!!.setBackgroundDrawable(inset)
    }

    private fun setUpViews() {
        titleTextView.text = title
        recyclerView.layoutManager = GridLayoutManager(windowContext, 4)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(4, windowContext.dpToPx(4)))
        recyclerView.adapter = GridItemAdapter {
            Log.d("SlidingUpMenu", "Menu item clicked: ${it.title}")
        }.apply { submitList(windowContext.getMenuList(menuResource)) }
    }

    fun logMessage(message: String) {
        Log.d(TAG, message)
    }
}
