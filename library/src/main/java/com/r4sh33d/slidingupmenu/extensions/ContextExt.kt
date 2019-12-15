package com.r4sh33d.slidingupmenu.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.r4sh33d.slidingupmenu.utils.MenuModel
import com.r4sh33d.slidingupmenu.utils.SizeFloat
import com.r4sh33d.slidingupmenu.utils.SizeInt
import kotlin.math.roundToInt

internal fun Context.getScreenSizeDp(): SizeFloat {
    val displayMetrics: DisplayMetrics = resources.displayMetrics
    val dpHeight = displayMetrics.heightPixels / displayMetrics.density
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return SizeFloat(dpHeight, dpWidth)
}

internal fun Context.dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
    ).roundToInt()
}

internal fun Context.getMenuList(@MenuRes menuRes: Int): ArrayList<MenuModel> {
    return PopupMenu(this, null).menu.run {
        MenuInflater(this@getMenuList).inflate(menuRes, this)
        val list = ArrayList<MenuModel>()
        for (index in 0 until size()) {
            list.add(MenuModel(getItem(index)))
        }
        list
    }
}

internal fun Context.getScreenSizePx(): SizeInt {
    val displayMetrics = resources.displayMetrics
    return SizeInt(
        displayMetrics.heightPixels,
        displayMetrics.widthPixels
    )
}

@SuppressLint("InlinedApi")
internal fun Context.getThemeBackgroundColor(): Int {
    val attributes = theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorBackgroundFloating))
    try {
        return attributes.getColor(0, Color.WHITE)
    } finally {
        attributes.recycle()
    }
}

internal fun Context.getFont(
    @FontRes fontRes: Int? = null,
    font: Typeface? = null
): Typeface? = font ?: if (fontRes != null) ResourcesCompat.getFont(this, fontRes) else null

internal fun Context.getColor(
    @ColorRes colorRes: Int? = null,
    @ColorInt color: Int? = null
): Int? = color ?: if (colorRes != null) ContextCompat.getColor(this, colorRes) else null

internal fun Context.getDrawableAsset(
    @DrawableRes iconDrawableRes: Int? = null,
    iconDrawable: Drawable? = null
): Drawable? {
    return iconDrawable ?: if (iconDrawableRes != null) ContextCompat.getDrawable(
        this,
        iconDrawableRes
    ) else null
}

internal fun Context.getDimension(
    @DimenRes dimenRes: Int? = null,
    @Px dimensionInPx: Int? = null
): Float? = dimensionInPx?.toFloat() ?: if (dimenRes != null) resources.getDimension(dimenRes) else null

internal fun Context.getTextString(
    @StringRes titleRes: Int? = null,
    text: String? = null
): String? {
    return text ?: if (titleRes != null) getString(titleRes) else null
}
