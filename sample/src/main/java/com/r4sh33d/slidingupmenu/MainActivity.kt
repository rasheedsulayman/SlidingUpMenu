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

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.AccelerateInterpolator
import android.widget.PopupMenu
import com.r4sh33d.R
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testMenu()
    }

    private fun testMenu() {
        val popupMenu = PopupMenu(this, null)
        val menuInflater = MenuInflater(this)
        val menu = popupMenu.menu
        menuInflater.inflate(R.menu.landing_page_menu, menu)

        // Now try to print

        for (index in 0 until menu.size()) {
            val menuItem = menu.getItem(index)
            Timber.d("MenuName: ${menuItem.title}, MenuTitle: ${menuItem.itemId}")
        }
    }

}
