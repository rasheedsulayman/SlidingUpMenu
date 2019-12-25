package com.r4sh33d.slidingupmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.r4sh33d.R
import com.r4sh33d.slidingupmenu.utils.MenuModel
import com.r4sh33d.slidingupmenu.utils.MenuType
import com.r4sh33d.slidingupmenu.utils.ScrollDirection
import kotlinx.android.synthetic.main.activity_single_test.*

class SingleTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_test)

        val  menuItems = listOf(
            MenuModel(1, "Model 1", getDrawableAsset(R.drawable.avast)),
            MenuModel(2, "Model 2", getDrawableAsset(R.drawable.zune)),
            MenuModel(3, "Model 3", getDrawableAsset(R.drawable.zune)),
            MenuModel(4, "Model 4", getDrawableAsset(R.drawable.linux)),
            MenuModel(5, "Model 5", getDrawableAsset(R.drawable.hotspot)),
            MenuModel(6, "Model 6", getDrawableAsset(R.drawable.origin)),
            MenuModel(7, "Model 7", getDrawableAsset(R.drawable.radar)),
            MenuModel(8, "Model 8", getDrawableAsset(R.drawable.google_docs))
        )

        button.setOnClickListener {
            SlidingUpMenu(this, R.menu.sample_menu).show {
                icon(R.drawable.hotspot)
                titleText(R.string.basic_title)
                cornerRadius(24f)
            }
        }
    }

    private fun getDrawableAsset(@DrawableRes drawableRes: Int) =
        ContextCompat.getDrawable(this, drawableRes)
}
