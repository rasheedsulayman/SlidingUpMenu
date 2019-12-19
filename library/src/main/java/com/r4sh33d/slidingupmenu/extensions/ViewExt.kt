package com.r4sh33d.slidingupmenu.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes

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

internal inline fun <reified T> Context.inflate(@LayoutRes layoutRes: Int): T {
    return LayoutInflater.from(this).inflate(layoutRes, null) as T
}