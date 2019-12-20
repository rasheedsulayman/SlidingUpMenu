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

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.r4sh33d.R
import com.r4sh33d.slidingupmenu.utils.MenuModel
import com.r4sh33d.slidingupmenu.utils.MenuType
import com.r4sh33d.slidingupmenu.utils.MenuType.*
import com.r4sh33d.slidingupmenu.utils.ScrollDirection
import com.r4sh33d.slidingupmenu.utils.ScrollDirection.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreference: SharedPreferences

    private val menuItems = listOf(
        MenuModel(1, "Model 1", getDrawableAsset(R.drawable.avast)),
        MenuModel(2, "Model 2", getDrawableAsset(R.drawable.zune)),
        MenuModel(3, "Model 3", getDrawableAsset(R.drawable.zune)),
        MenuModel(4, "Model 4", getDrawableAsset(R.drawable.linux)),
        MenuModel(5, "Model 5", getDrawableAsset(R.drawable.hotspot)),
        MenuModel(6, "Model 6", getDrawableAsset(R.drawable.origin)),
        MenuModel(7, "Model 7", getDrawableAsset(R.drawable.radar)),
        MenuModel(8, "Model 8", getDrawableAsset(R.drawable.google_docs))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = getPreferences(Context.MODE_PRIVATE)
        setTheme(getActivityTheme())
        setContentView(R.layout.activity_main)
        setUpSample()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val theme = when (item.itemId) {
            R.id.dark_theme -> DARK_THEME
            R.id.custom_style_theme -> CUSTOM_RESOURCE_THEME
            else -> LIGHT_THEME
        }
        sharedPreference.edit(true) {
            putString(KEY_THEME, theme)
        }
        recreate()
        return true
    }

    private fun setUpSample() {

        basic_menu_resource.setOnClickListener {
            SlidingUpMenu(this, R.menu.sample_menu).show {
                titleText(R.string.basic_title)
                menuModelSelected { slidingUpMenu, menuModel, position ->
                    showClick(menuModel, position)
                }
            }
        }

        basic_array_list.setOnClickListener {
            SlidingUpMenu(this, menuModelItems = menuItems).show {
                titleText(R.string.basic_title)
                menuModelSelected { _, menuModel, position ->
                    showClick(menuModel, position)
                }
            }
        }

        grid_horizontal.setOnClickListener {
            SlidingUpMenu(this, R.menu.sample_menu).show {
                titleText(R.string.basic_title)
                scrollDirection(HORIZONTAL)
                menuType(GRID)
                menuModelSelected { _, menuModel, position ->
                    showClick(menuModel, position)
                }
            }
        }

        list_horizontal.setOnClickListener {
            SlidingUpMenu(this, R.menu.sample_menu).show {
                titleText(R.string.basic_title)
                scrollDirection(HORIZONTAL)
                menuType(LIST)
                menuModelSelected { _, menuModel, position ->
                    showClick(menuModel, position)
                }
            }
        }

        grid_vertical.setOnClickListener {
            SlidingUpMenu(this, R.menu.sample_menu).show {
                titleText(R.string.basic_title)
                scrollDirection(VERTICAL)
                menuType(GRID)
                menuModelSelected { _, menuModel, position ->
                    showClick(menuModel, position)
                }
            }
        }

        list_vertical.setOnClickListener {
            SlidingUpMenu(this, R.menu.sample_menu).show {
                titleText(R.string.basic_title)
                scrollDirection(VERTICAL)
                menuType(LIST)
                menuModelSelected { _, menuModel, position ->
                    showClick(menuModel, position)
                }
            }
        }

        basic_icon.setOnClickListener {
            SlidingUpMenu(this, R.menu.sample_menu).show {
                titleText(R.string.basic_title)
                icon(R.drawable.hotspot)
                menuModelSelected { _, menuModel, position ->
                    showClick(menuModel, position)
                }
            }
        }

        menu_resource_dynamic_list.setOnClickListener {
            SlidingUpMenu(this, R.menu.sample_menu, menuItems).show {
                titleText(R.string.basic_title)
                icon(R.drawable.hotspot)
                menuModelSelected { _, menuModel, position ->
                    showClick(menuModel, position)
                }
            }
        }
    }

    private fun getDrawableAsset(@DrawableRes drawableRes: Int) =
        ContextCompat.getDrawable(this, drawableRes)

    @StyleRes
    private fun getActivityTheme(): Int =
        when (sharedPreference.getString(KEY_THEME, LIGHT_THEME)) {
            DARK_THEME -> R.style.AppThemeDark
            LIGHT_THEME -> R.style.AppThemeLight
            CUSTOM_RESOURCE_THEME -> R.style.AppThemeLight_CustomResource
            else -> R.style.AppThemeLight
        }

    private fun showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun showClick(menuModel: MenuModel, position: Int) =
        showToast("Menu item '${menuModel.title}' with id '${menuModel.id}' clicked at position '$position'")

    companion object {
        private const val KEY_THEME = "theme_key"
        private const val DARK_THEME = "dark_theme"
        private const val LIGHT_THEME = "light_theme"
        private const val CUSTOM_RESOURCE_THEME = "custom_resource_theme"
    }
}
