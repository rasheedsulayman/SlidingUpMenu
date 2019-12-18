/**
 * Designed and developed by Aidan Follestad (@afollestad)
 * Modified by Rasheed Sulayman (@r4sh33d) for use in SlidingUpMenu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.r4sh33d.slidingupmenu.utils

import android.R.attr
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.*
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import androidx.core.content.ContextCompat
import com.r4sh33d.slidingupmenu.R
import com.r4sh33d.slidingupmenu.SlidingUpMenu

@RestrictTo(LIBRARY_GROUP)
object SlidingUpMenuUtil {

    @RestrictTo(LIBRARY_GROUP)
    fun resolveString(
        slidingUpMenu: SlidingUpMenu,
        @StringRes res: Int? = null,
        @StringRes fallback: Int? = null,
        html: Boolean = false
    ): CharSequence? = resolveString(
        context = slidingUpMenu.context,
        res = res,
        fallback = fallback,
        html = html
    )

    @RestrictTo(LIBRARY_GROUP)
    fun resolveString(
        context: Context,
        @StringRes res: Int? = null,
        @StringRes fallback: Int? = null,
        html: Boolean = false
    ): CharSequence? {
        val resourceId = res ?: (fallback ?: 0)
        if (resourceId == 0) return null
        val text = context.resources.getText(resourceId)
        if (html) {
            @Suppress("DEPRECATION")
            return Html.fromHtml(text.toString())
        }
        return text
    }

    @RestrictTo(LIBRARY_GROUP)
    fun resolveDrawable(
        context: Context,
        @DrawableRes res: Int? = null,
        @AttrRes attr: Int? = null,
        fallback: Drawable? = null
    ): Drawable? {
        if (attr != null) {
            val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
            try {
                var d = a.getDrawable(0)
                if (d == null && fallback != null) {
                    d = fallback
                }
                return d
            } finally {
                a.recycle()
            }
        }
        if (res == null) return fallback
        return ContextCompat.getDrawable(context, res)
    }

    @RestrictTo(LIBRARY_GROUP)
    @ColorInt
    fun resolveColor(
        context: Context,
        @ColorRes res: Int? = null,
        @AttrRes attr: Int? = null,
        fallback: (() -> Int)? = null
    ): Int {
        if (attr != null) {
            val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
            try {
                val result = a.getColor(0, 0)
                if (result == 0 && fallback != null) {
                    return fallback()
                }
                return result
            } finally {
                a.recycle()
            }
        }
        return ContextCompat.getColor(context, res ?: 0)
    }

    @RestrictTo(LIBRARY_GROUP)
    fun resolveColors(
        context: Context,
        attrs: IntArray,
        fallback: ((forAttr: Int) -> Int)? = null
    ): IntArray {
        val a = context.theme.obtainStyledAttributes(attrs)
        try {
            return (attrs.indices).map { index ->
                val color = a.getColor(index, 0)
                return@map if (color != 0) {
                    color
                } else {
                    fallback?.invoke(attrs[index]) ?: 0
                }
            }
                .toIntArray()
        } finally {
            a.recycle()
        }
    }

    @RestrictTo(LIBRARY_GROUP)
    fun resolveInt(
        context: Context,
        @AttrRes attr: Int,
        defaultValue: Int = 0
    ): Int {
        val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
        try {
            return a.getInt(0, defaultValue)
        } finally {
            a.recycle()
        }
    }

    @RestrictTo(LIBRARY_GROUP)
    fun resolveDimen(
        context: Context,
        @AttrRes attr: Int,
        defaultValue: Float = 0f
    ): Float {
        val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
        try {
            return a.getDimension(0, defaultValue)
        } finally {
            a.recycle()
        }
    }

    @RestrictTo(LIBRARY_GROUP)
    fun Int.isColorDark(threshold: Double = 0.5): Boolean {
        if (this == Color.TRANSPARENT) {
            return false
        }
        val darkness =
            1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
        return darkness >= threshold
    }

    @RestrictTo(LIBRARY_GROUP)
    fun TextView?.maybeSetTextColor(
        context: Context,
        @AttrRes attrRes: Int?,
        @AttrRes hintAttrRes: Int? = null
    ) {
        if (this == null || (attrRes == null && hintAttrRes == null)) return
        if (attrRes != null) {
            resolveColor(context, attr = attrRes).ifNotZero(this::setTextColor)
        }
        if (hintAttrRes != null) {
            resolveColor(context, attr = hintAttrRes).ifNotZero(this::setHintTextColor)
        }
    }

    @RestrictTo(LIBRARY_GROUP)
    inline fun Int?.ifNotZero(block: (value: Int) -> Unit) {
        if (this != null && this != 0) {
            block(this)
        }
    }

    @RestrictTo(LIBRARY_GROUP)
    fun assertOneSet(
        method: String,
        b: Any?,
        a: Int?
    ) {
        if (a == null && b == null) {
            throw IllegalArgumentException("$method: You must specify a resource ID or literal value")
        }
    }

    @StyleRes
    internal fun getDialogTheme(context: Context): Int =
        if (inferThemeIsLight(context)) R.style.SM_Light_BottomSheet else R.style.SM_Dark_BottomSheet

    @CheckResult
    internal fun inferThemeIsLight(context: Context): Boolean {
        return resolveColor(context = context, attr = attr.textColorPrimary).isColorDark()
    }
}
