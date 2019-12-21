package com.r4sh33d.slidingupmenu

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.util.Log
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.r4sh33d.slidingupmenu.adapters.ViewPagerAdapter
import com.r4sh33d.slidingupmenu.extensions.getMenuList
import com.r4sh33d.slidingupmenu.extensions.getScreenSizePx
import com.r4sh33d.slidingupmenu.extensions.inflate
import com.r4sh33d.slidingupmenu.extensions.onGlobalLayout
import com.r4sh33d.slidingupmenu.utils.*
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.assertOneSet
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.getDialogTheme
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.resolveDimen
import com.r4sh33d.slidingupmenu.views.WrapContentViewPager
import kotlin.math.abs
import kotlin.math.min

//TODO add cancelable on touch outside to the docs

class SlidingUpMenu(
    context: Context,
    @MenuRes val menuResource: Int? = null,
    private val menuModelItems: List<MenuModel>? = null
) : BottomSheetDialog(context, getDialogTheme(context)) {

    //Views
    internal val dialogRootView = context.inflate<LinearLayout>(R.layout.dialog_root_view)
    private val titleTextView = dialogRootView.findViewById<TextView>(R.id.dialogTitleTextView)
    private val viewPagerContainerLinearLayout =
        dialogRootView.findViewById<LinearLayout>(R.id.viewPagerContainerLinearLayout)
    private val iconImageView = dialogRootView.findViewById<ImageView>(R.id.iconImageView)
    private var rootViewFrameLayoutWrapper: FrameLayout

    private val menuItemsList = mutableListOf<MenuModel>()

    //Other fields
    internal var menuType = MenuType.GRID
    internal var scrollDirection = ScrollDirection.HORIZONTAL
    internal var menuModelSelectedListener: MenuModelSelectedListener? = null
    internal var dismissMenuOnItemSelected: Boolean = true
    private var titleTextFont: Typeface? = null
    var bodyTextFont: Typeface? = null
        internal set
    private var cornerRadius: Float? = null

    init {
        setContentView(dialogRootView)
        rootViewFrameLayoutWrapper =
            window!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        setUpWindowContent()
        populateMenuModelItems()
        invalidateBackgroundColorAndRadius()
        titleTextFont = font(attr = R.attr.sm_title_text_font)
        bodyTextFont = font(attr = R.attr.sm_body_text_font)
    }

    private fun setUpWindowContent() {
        val marginLeftRight = context.getScreenSizePx().run {
            if (width == min(width, height)) 0 else ((abs(width - height)) / 2)
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

    private fun populateMenuModelItems() {
        //Try to build the menu list
        if (menuResource != null) menuItemsList.addAll(context.getMenuList(menuResource))
        if (menuModelItems != null) menuItemsList.addAll(menuModelItems)
        require(menuItemsList.size > 0) {
            "No menu item(s) to work with. Please specify items with a non-empty menu resource " +
                    "and/or supply MenuModel list to SlidingUpMenu constructor"
        }
    }

    private fun setUpViews() {
        val viewPager = WrapContentViewPager(context, scrollDirection)
        val tabLayout = context.inflate<TabLayout>(R.layout.tab_layout)
        viewPagerContainerLinearLayout.addView(viewPager)
        viewPagerContainerLinearLayout.addView(tabLayout)
        viewPager.adapter = ViewPagerAdapter(this, splitMenuList(menuItemsList, scrollDirection))
        tabLayout.setupWithViewPager(viewPager, true)
        if (scrollDirection == ScrollDirection.HORIZONTAL) {
            dialogRootView.onGlobalLayout {
                behavior.peekHeight = dialogRootView.height
                logMessage("dialog height: ${dialogRootView.height}")
            }
        }
    }

    fun titleText(
        @StringRes titleRes: Int? = null, titleText: String? = null
    ): SlidingUpMenu {
        assertOneSet("title", titleText, titleRes)
        populateText(
            titleTextView,
            textRes = titleRes,
            text = titleText,
            typeface = titleTextFont,
            textColor = R.attr.sm_title_text_color,
            canHideParent = true
        )
        return this
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
        setUpViews()
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

    fun icon(
        @DrawableRes res: Int? = null,
        drawable: Drawable? = null
    ): SlidingUpMenu {
        assertOneSet("icon", drawable, res)
        populateIcon(
            iconImageView,
            iconRes = res,
            icon = drawable
        )
        return this
    }

    fun cornerRadius(
        dpLiteral: Float? = null,
        @DimenRes dpRes: Int? = null
    ): SlidingUpMenu {
        assertOneSet("cornerRadius", dpLiteral, dpRes)
        cornerRadius = if (dpRes != null) {
            context.resources.getDimension(dpRes)
        } else {
            val displayMetrics = context.resources.displayMetrics
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpLiteral!!, displayMetrics)
        }
        invalidateBackgroundColorAndRadius()
        return this
    }

    private fun invalidateBackgroundColorAndRadius() {
        val backgroundColor = resolveColor(attr = R.attr.sm_background_color) {
            resolveColor(attr = R.attr.colorBackgroundFloating)
        }
        val cornerRadius = cornerRadius ?: resolveDimen(context, attr = R.attr.sm_corner_radius)
        rootViewFrameLayoutWrapper.background = GradientDrawable().apply {
            cornerRadii = getTopLeftCornerRadius(cornerRadius)
            setColor(backgroundColor)
        }
    }

    private fun logMessage(message: String) {
        Log.d("SlidingUpMenu", message)
    }
}