package com.r4sh33d.slidingupmenu.extensions

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
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
    context.getTextString(textRes, text)?.let {
        setText(it)
    }
}

internal fun TextView.saveSetTextColor(
    @ColorRes colorRes: Int? = null,
    @ColorInt color: Int? = null
) {
    context.getColor(colorRes, color)?.let {
        setTextColor(it)
    }
}

internal fun ImageView.saveSetIconDrawable(
    @DrawableRes iconDrawableRes: Int? = null,
    iconDrawable: Drawable? = null
) {
    context.getDrawableAsset(iconDrawableRes, iconDrawable)?.let {
        setImageDrawable(it)
        show()
    }
}

internal fun TextView.saveSetTypeFace(@FontRes fontRes: Int? = null, font: Typeface? = null) {
    context.getFont(fontRes, font)?.let {
        typeface = it
    }
}