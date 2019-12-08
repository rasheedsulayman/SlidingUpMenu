package com.r4sh33d.slidingupmenu.extensions

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

internal inline fun View.onGlobalLayout(crossinline block: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block()
        }
    })
}

internal fun TextView.saveSetText(
    @StringRes textRes: Int? = null,
    text: String? = null
) {
    getTextString(context, textRes, text)?.let {
        setText(it)
    }
}

internal fun TextView.saveSetTextColor(
    @ColorRes colorRes: Int? = null,
    @ColorInt color: Int? = null
) {
    getColor(context, colorRes, color)?.let {
        setTextColor(it)
    }
}

internal fun ImageView.saveSetIconDrawable(
    @DrawableRes iconDrawableRes: Int? = null,
    iconDrawable: Drawable? = null
) {
    getDrawableAsset(context, iconDrawableRes, iconDrawable)?.let {
        setImageDrawable(it)
        show()
    }
}

internal fun TextView.saveSetTypeFace(@FontRes fontRes: Int? = null, font: Typeface? = null) {
    getFont(context, fontRes, font)?.let {
        typeface = it
    }
}

internal fun getFont(
    context: Context, @FontRes fontRes: Int? = null,
    font: Typeface? = null
): Typeface? = font ?: if (fontRes != null) ResourcesCompat.getFont(context, fontRes) else null

internal fun getColor(
    context: Context,
    @ColorRes colorRes: Int? = null,
    @ColorInt color: Int? = null
): Int? = color ?: if (colorRes != null) ContextCompat.getColor(context, colorRes) else null

internal fun getDrawableAsset(
    context: Context, @DrawableRes iconDrawableRes: Int? = null,
    iconDrawable: Drawable? = null
): Drawable? {
    return iconDrawable ?: if (iconDrawableRes != null) ContextCompat.getDrawable(
        context,
        iconDrawableRes
    ) else null
}

internal fun getTextString(
    context: Context,
    @StringRes titleRes: Int? = null,
    text: String? = null
): String? {
    return text ?: if (titleRes != null) context.getString(titleRes) else null
}