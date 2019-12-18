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
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.assertOneSet
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.getDialogTheme
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.resolveDimen
import com.r4sh33d.slidingupmenu.views.WrapContentViewPager
import kotlin.math.abs
import kotlin.math.min


class SlidingUpMenu(
    context: Context,
    @MenuRes val menuResource: Int? = null,
    private val menuModelItems: List<MenuModel>? = null
) : BottomSheetDialog(context, R.style.Theme_Design_BottomSheetDialog) {

    //Views
    internal val dialogRootView =
        LayoutInflater.from(context).inflate(R.layout.dialog_root_view, null)
    private val titleTextView = dialogRootView.findViewById<TextView>(R.id.dialogTitleTextView)
    private val tabLayout = dialogRootView.findViewById<TabLayout>(R.id.tabLayout)
    private val viewPagerContainerLinearLayout =
        dialogRootView.findViewById<LinearLayout>(R.id.viewPagerContainerLinearLayout)
    private val iconImageView = dialogRootView.findViewById<ImageView>(R.id.iconImageView)
    private var rootViewFrameLayoutWrapper: FrameLayout

    private lateinit var viewPager: WrapContentViewPager
    private val menuItemsList = mutableListOf<MenuModel>()

    //Other fields
    internal var menuType = MenuType.GRID
    internal var scrollDirection = ScrollDirection.HORIZONTAL
    internal var menuModelSelectedListener: MenuModelSelectedListener? = null
    internal var dismissMenuOnItemSelected: Boolean = true

    var titleTextFont: Typeface? = null
        internal set
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

    private fun configureScreen() {
        setUpViews()
        if (scrollDirection == ScrollDirection.HORIZONTAL) {
            dialogRootView.onGlobalLayout {
                behavior.peekHeight = dialogRootView.height
                logMessage("dialog height: ${dialogRootView.height}")
            }
        }
    }

    private fun setUpViews() {
        viewPager = WrapContentViewPager(context, scrollDirection)
        viewPagerContainerLinearLayout.addView(viewPager, 0)
        viewPager.adapter = ViewPagerAdapter(this, splitMenuList(menuItemsList, scrollDirection))
        tabLayout.setupWithViewPager(viewPager, true)
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
            textColor = R.attr.sm_title_text_color
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
            logMessage("About to get color floating")
            resolveColor(attr = R.attr.colorBackgroundFloating)
        }
        val hexColor = String.format("#%06X", (0xFFFFFF and backgroundColor))
        logMessage("Color string is: $hexColor")
        val cornerRadius = cornerRadius ?: resolveDimen(context, attr = R.attr.sm_corner_radius)
        rootViewFrameLayoutWrapper.background = GradientDrawable().apply {
            cornerRadii = getTopLeftCornerRadius(cornerRadius)
            setColor(backgroundColor)
        }
    }

    fun logMessage(message: String) {
        Log.d("SlidingUpMenu", message)
    }
}