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

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import com.r4sh33d.slidingupmenu.R
import com.r4sh33d.slidingupmenu.SlidingUpMenu
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.assertOneSet
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.ifNotZero
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.maybeSetTextColor
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.resolveColor
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.resolveColors
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.resolveDrawable
import com.r4sh33d.slidingupmenu.utils.SlidingUpMenuUtil.resolveString

internal fun SlidingUpMenu.populateIcon(
    imageView: ImageView,
    @DrawableRes iconRes: Int?,
    icon: Drawable?
) {
    val drawable = resolveDrawable(context, res = iconRes, fallback = icon)
    if (drawable != null) {
        (imageView.parent as View).visibility = View.VISIBLE
        imageView.visibility = View.VISIBLE
        imageView.setImageDrawable(drawable)
    } else {
        imageView.visibility = View.GONE
    }
}

internal fun SlidingUpMenu.populateText(
    textView: TextView,
    @StringRes textRes: Int? = null,
    text: CharSequence? = null,
    @StringRes fallback: Int = 0,
    typeface: Typeface?,
    textColor: Int? = null
) {
    val value = text ?: resolveString(this, textRes, fallback)
    if (value != null) {
        (textView.parent as View).visibility = View.VISIBLE
        textView.visibility = View.VISIBLE
        textView.text = value
        if (typeface != null) {
            textView.typeface = typeface
        }
        textView.maybeSetTextColor(context, textColor)
    } else {
        textView.visibility = View.GONE
    }
}

internal fun SlidingUpMenu.hideKeyboard() {
    val imm =
        context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = currentFocus
    if (currentFocus != null) {
        currentFocus.windowToken
    } else {
        dialogRootView.windowToken
    }.let {
        imm.hideSoftInputFromWindow(it, 0)
    }
}

@CheckResult
internal fun SlidingUpMenu.font(
    @FontRes res: Int? = null,
    @AttrRes attr: Int? = null
): Typeface? {
    assertOneSet("font", attr, res)
    if (res != null) {
        return safeGetFont(context, res)
    }
    requireNotNull(attr)
    val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
    try {
        val resId = a.getResourceId(0, 0)
        if (resId == 0) return null
        return safeGetFont(context, resId)
    } finally {
        a.recycle()
    }
}

private fun safeGetFont(context: Context, @FontRes res: Int): Typeface? {
    return try {
        ResourcesCompat.getFont(context, res)
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

@ColorInt
@CheckResult
internal fun SlidingUpMenu.resolveColor(
    @ColorRes res: Int? = null,
    @AttrRes attr: Int? = null,
    fallback: (() -> Int)? = null
): Int = resolveColor(context, res, attr, fallback)

@CheckResult
internal fun SlidingUpMenu.resolveColors(
    attrs: IntArray,
    fallback: ((forAttr: Int) -> Int)? = null
) = resolveColors(context, attrs, fallback)

@ColorInt
@CheckResult
internal fun Int.adjustAlpha(alpha: Float): Int {
    return Color.argb((255 * alpha).toInt(), Color.red(this), Color.green(this), Color.blue(this))
}

/** @author Aidan Follestad (@afollestad) */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun SlidingUpMenu.getItemSelector(): Drawable? {
    val drawable = resolveDrawable(context = context, attr = R.attr.sm_item_selector)
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && drawable is RippleDrawable) {
        resolveColor(attr = R.attr.sm_ripple_color).ifNotZero {
            drawable.setColor(android.content.res.ColorStateList.valueOf(it))
        }
    }
    return drawable
}