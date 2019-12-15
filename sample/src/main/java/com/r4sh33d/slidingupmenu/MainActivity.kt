/**
 * Copyright (c) Rasheed Sulayman.
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
package com.r4sh33d.slidingupmenu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.AccelerateInterpolator
import android.widget.PopupMenu
import android.widget.Toast
import com.r4sh33d.R
import com.r4sh33d.slidingupmenu.utils.MenuType
import com.r4sh33d.slidingupmenu.utils.ScrollDirection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            SlidingUpMenu(this, R.menu.landing_page_menu).show {
                cornerRadius(dimensionInPx = 64)
                backgroundColor(colorInt = Color.WHITE)
                titleText(titleText = "A gentle menu")
                titleColor(R.color.colorAccent)
                dismissOnMenuItemSelected(false)
                bodyTextColor(colorInt = Color.BLUE)
                scrollDirection(ScrollDirection.HORIZONTAL)
                menuType(MenuType.GRID)
                menuModelSelected { slidingUpMenu, menuModel, position ->
                    Toast.makeText(
                        this@MainActivity,
                        "Item selected ${menuModel.title} at position: $position",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
