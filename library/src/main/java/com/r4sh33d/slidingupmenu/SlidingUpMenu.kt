package com.r4sh33d.slidingupmenu

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.MenuRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.r4sh33d.slidingupmenu.adapters.ViewPagerAdapter
import com.r4sh33d.slidingupmenu.extensions.getMenuList
import com.r4sh33d.slidingupmenu.extensions.getScreenSizePx
import com.r4sh33d.slidingupmenu.extensions.onGlobalLayout
import com.r4sh33d.slidingupmenu.utils.splitMenuList
import com.r4sh33d.slidingupmenu.views.WrapContentViewPager
import kotlin.math.abs
import kotlin.math.min


class SlidingUpMenu(
    private val windowContext: Context,
    private var title: String,
    @MenuRes private val menuResource: Int
) : BottomSheetDialog(windowContext) {
    val TAG = "SlidingUpMenu"
    private val dialogRootView =
        LayoutInflater.from(windowContext).inflate(R.layout.dialog_root_view, null)
    private val titleTextView = dialogRootView.findViewById<TextView>(R.id.dialogTitleTextView)
    private val viewPager = dialogRootView.findViewById<WrapContentViewPager>(R.id.view_pager)
    private val tabLayout = dialogRootView.findViewById<TabLayout>(R.id.tabLayout)

    init {
        setUpWindowBackground()
        setUpViews()
        setContentView(dialogRootView)
        dialogRootView.onGlobalLayout {
            behavior.peekHeight = dialogRootView.height
            logMessage("dialog height: ${dialogRootView.height}")
        }
    }

    private fun setUpWindowBackground() {
        val marginLeftRight = windowContext.getScreenSizePx().run {
            if (width == min(width, height)) 0 else ((0.8 * abs(width - height)) / 2).toInt()
        }
        val inset = InsetDrawable(
            ColorDrawable(Color.TRANSPARENT),
            marginLeftRight,
            0,
            marginLeftRight,
            0
        )
        window!!.setBackgroundDrawable(inset)
    }

    private fun setUpViews() {
        titleTextView.text = title
        viewPager.adapter =
            ViewPagerAdapter(
                windowContext,
                splitMenuList(
                    context.getMenuList(menuResource)
                )
            )
        tabLayout.setupWithViewPager(viewPager, true)
    }

    fun logMessage(message: String) {
        Log.d(TAG, message)
    }
}
