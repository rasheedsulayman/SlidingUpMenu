package com.r4sh33d.slidingupmenu

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.r4sh33d.slidingupmenu.adapters.ViewPagerAdapter
import com.r4sh33d.slidingupmenu.extensions.*
import com.r4sh33d.slidingupmenu.utils.*
import com.r4sh33d.slidingupmenu.views.WrapContentViewPager
import kotlin.math.abs
import kotlin.math.min


class SlidingUpMenu(
    internal val windowContext: Context,
    @MenuRes menuResource: Int? = null,
    menuModelItems: MutableList<MenuModel>? = null
) : BottomSheetDialog(windowContext, R.style.Theme_Design_BottomSheetDialog) {

    //Views
    private val dialogRootView =
        LayoutInflater.from(windowContext).inflate(R.layout.dialog_root_view, null)
    private val titleTextView = dialogRootView.findViewById<TextView>(R.id.dialogTitleTextView)
    private val tabLayout = dialogRootView.findViewById<TabLayout>(R.id.tabLayout)
    private val viewPagerContainerLinearLayout =
        dialogRootView.findViewById<LinearLayout>(R.id.viewPagerContainerLinearLayout)
    private val iconImageView = dialogRootView.findViewById<ImageView>(R.id.iconImageView)
    private lateinit var viewPager: WrapContentViewPager
    private val menuItemsList = mutableListOf<MenuModel>()

    //Other fields
    internal var menuType = MenuType.GRID
    internal var scrollDirection = ScrollDirection.HORIZONTAL
    internal val bodyTextStyle = BodyTextStyle()
    internal var menuModelSelectedListener: MenuModelSelectedListener? = null
    var dismissMenuOnItemSelected: Boolean = true
        internal set

    private var backgroundColor = windowContext.getThemeBackgroundColor()
    private var cornerRadius: Float = 0f

    init {
        val marginLeftRight = windowContext.getScreenSizePx().run {
            if (width == min(width, height)) 0 else ((abs(width - height)) / 2).toInt()
        }
        val inset = InsetDrawable(
            ColorDrawable(Color.TRANSPARENT),
            marginLeftRight,
            0,
            marginLeftRight,
            0
        )
        window!!.setBackgroundDrawable(inset)
        //Try to build the menu list
        if (menuResource != null) menuItemsList.addAll(windowContext.getMenuList(menuResource))
        if (menuModelItems != null) menuItemsList.addAll(menuModelItems)



        require(menuItemsList.size > 0) {
            "No menu item(s) to work with. Please specify items with a non-empty menu resource and/or supply MenuModel " +
                    "list to SlidingUpMenu constructor"
        }
    }

    private fun configureScreen() {
        setContentView(dialogRootView)
        setUpViews()
        if (scrollDirection == ScrollDirection.HORIZONTAL) {
            dialogRootView.onGlobalLayout {
                behavior.peekHeight = dialogRootView.height
                logMessage("dialog height: ${dialogRootView.height}")
            }
        }
    }

    private fun setUpViews() {
        val v =
            window!!.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        v.background = GradientDrawable().apply {
            cornerRadius = this@SlidingUpMenu.cornerRadius
            setColor(backgroundColor)
        }

        viewPager = WrapContentViewPager(windowContext, scrollDirection)
        viewPagerContainerLinearLayout.addView(viewPager, 0)
        viewPager.adapter = ViewPagerAdapter(this, splitMenuList(menuItemsList, scrollDirection))
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

    inline fun show(block: SlidingUpMenu.() -> Unit): SlidingUpMenu {
        block()
        show()
        return this
    }

    fun menuModelSelected(menuModelSelectedListener: MenuModelSelectedListener): SlidingUpMenu {
        this.menuModelSelectedListener = menuModelSelectedListener
        return this
    }

    fun dismissOnMenuItemSelected(value: Boolean): SlidingUpMenu {
        dismissMenuOnItemSelected = value
        return this
    }

    fun titleText(@StringRes titleRes: Int? = null, titleText: String? = null): SlidingUpMenu {
        titleTextView.saveSetText(titleRes, titleText)
        return this
    }

    fun titleColor(@ColorRes colorRes: Int? = null, @ColorInt colorInt: Int? = null): SlidingUpMenu {
        titleTextView.saveSetTextColor(colorRes, colorInt)
        return this
    }

    fun icon(@DrawableRes iconDrawableRes: Int? = null, iconDrawable: Drawable? = null): SlidingUpMenu {
        iconImageView.saveSetIconDrawable(iconDrawableRes, iconDrawable)
        return this
    }

    fun titleFont(@FontRes fontRes: Int? = null, font: Typeface): SlidingUpMenu {
        titleTextView.saveSetTypeFace(fontRes, font)
        return this
    }

    fun bodyTextColor(@ColorRes colorRes: Int? = null, @ColorInt colorInt: Int? = null): SlidingUpMenu {
        bodyTextStyle.textColor = getColor(windowContext, colorRes, colorInt)
        return this
    }

    fun bodyTextFont(@FontRes fontRes: Int? = null, font: Typeface): SlidingUpMenu {
        bodyTextStyle.font = getFont(windowContext, fontRes, font)
        return this
    }

    fun cornerRadius(@DimenRes dimenRes: Int? = null, dimensionInPx: Int? = null): SlidingUpMenu {
        cornerRadius = getDimension(windowContext, dimenRes, dimensionInPx) ?: cornerRadius
        return this
    }

    fun backgroundColor(@ColorRes colorRes: Int? = null, @ColorInt colorInt: Int? = null): SlidingUpMenu {
        backgroundColor = getColor(windowContext, colorRes, colorInt) ?: backgroundColor
        return this
    }


    fun logMessage(message: String) {
        Log.d("SlidingUpMenu", message)
    }
}