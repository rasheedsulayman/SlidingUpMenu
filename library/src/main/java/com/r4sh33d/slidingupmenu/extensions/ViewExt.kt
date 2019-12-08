package com.r4sh33d.slidingupmenu.extensions

import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

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
    text: String? = null,
    @StringRes titleRes: Int? = null
) {
    val newText = text ?: if (titleRes != null) context.getString(titleRes) else null
    if (newText != null) setText(newText)
}

internal fun TextView.saveSetTextColor(
    @ColorInt color: Int? = null,
    @ColorRes colorRes: Int? = null
) {
    val newColor = color ?: if (colorRes != null) ContextCompat.getColor(context, colorRes) else null
    if (newColor != null) setTextColor(newColor)
}
