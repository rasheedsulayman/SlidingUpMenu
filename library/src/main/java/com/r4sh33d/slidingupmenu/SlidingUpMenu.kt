package com.r4sh33d.slidingupmenu

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.r4sh33d.slidingupmenu.adapters.ViewPagerAdapter
import com.r4sh33d.slidingupmenu.extensions.*
import com.r4sh33d.slidingupmenu.extensions.getMenuList
import com.r4sh33d.slidingupmenu.extensions.getScreenSizePx
import com.r4sh33d.slidingupmenu.extensions.onGlobalLayout
import com.r4sh33d.slidingupmenu.extensions.saveSetText
import com.r4sh33d.slidingupmenu.utils.MenuModel
import com.r4sh33d.slidingupmenu.utils.MenuType
import com.r4sh33d.slidingupmenu.utils.ScrollDirection
import com.r4sh33d.slidingupmenu.utils.splitMenuList
import com.r4sh33d.slidingupmenu.views.WrapContentViewPager
import kotlin.math.abs
import kotlin.math.min


class SlidingUpMenu(
    private val windowContext: Context,
    @MenuRes menuResource: Int? = null,
    menuModelItems: MutableList<MenuModel>? = null
) : BottomSheetDialog(windowContext) {

    //Views
    private val dialogRootView =
        LayoutInflater.from(windowContext).inflate(R.layout.dialog_root_view, null)
    private val titleTextView = dialogRootView.findViewById<TextView>(R.id.dialogTitleTextView)
    private val tabLayout = dialogRootView.findViewById<TabLayout>(R.id.tabLayout)
    private val viewPagerContainerLinearLayout =
        dialogRootView.findViewById<LinearLayout>(R.id.viewPagerContainerLinearLayout)
    private val iconImageView = dialogRootView.findViewById<ImageView>(R.id.iconImageView)
    private var viewPager: WrapContentViewPager

    //Other fields
    private var menuType = MenuType.GRID
    private var scrollDirection = ScrollDirection.HORIZONTAL
    private val menuItemsList = ArrayList<MenuModel>()

    private var bodyColor: Int? = null

    init {
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
        viewPager = WrapContentViewPager(windowContext, scrollDirection)
        viewPagerContainerLinearLayout.addView(viewPager, 0)

        //Try to build the menu list
        if (menuResource != null) menuItemsList.addAll(windowContext.getMenuList(menuResource))
        if (menuModelItems != null) menuItemsList.addAll(menuModelItems)
        if (menuItemsList.size < 1) throw IllegalArgumentException(
            "No menu item(s) to work with." + "Please specify items with a menu resource and/or supply MenuModel " +
                    "list to SlidingUpMenu constructor"
        )
    }

    fun titleText(@StringRes titleRes: Int? = null, title: String? = null): SlidingUpMenu {
        titleTextView.saveSetText(titleRes, title)
        return this
    }

    fun titleColor(@ColorRes colorRes: Int? = null, @ColorInt colorInt: Int? = null) : SlidingUpMenu {
        titleTextView.saveSetTextColor(colorRes, colorInt)
        return this
    }

    fun icon(@DrawableRes iconDrawableRes: Int? = null, iconDrawable: Drawable? = null) : SlidingUpMenu {
        iconImageView.saveSetIconDrawable(iconDrawableRes, iconDrawable)
        return this
    }

    fun titleFont(@FontRes fontRes: Int? = null, font: Typeface){

    }

    private fun configureScreen() {
        setUpViews()
        setContentView(dialogRootView)
        if (scrollDirection == ScrollDirection.HORIZONTAL) {
            dialogRootView.onGlobalLayout {
                behavior.peekHeight = dialogRootView.height
                logMessage("dialog height: ${dialogRootView.height}")
            }
        }
    }

    private fun setUpViews() {
        viewPager.adapter = ViewPagerAdapter(
            windowContext,
            splitMenuList(menuItemsList, scrollDirection),
            menuType,
            scrollDirection
        )
        tabLayout.setupWithViewPager(viewPager, true)
    }

    fun menuType(menuType: MenuType): SlidingUpMenu {
        this.menuType = menuType
        return this
    }

    fun scrollDirection(scrollDirection: ScrollDirection): SlidingUpMenu {
        this.scrollDirection = scrollDirection
        return this
    }

    override fun show() {
        configureScreen()
        super.show()
    }

    fun logMessage(message: String) {
        Log.d("SlidingUpMenu", message)
    }
}
