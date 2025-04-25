package com.example.fashionhome.ui.screens.cart_screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import com.example.fashionhome.helper.ManagementCart
import com.example.fashionhome.ui.screens.BaseActivity
import kotlin.math.roundToInt

class CartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartScreen(
                ManagementCart(this)
            ) {
                finish()
            }
        }
    }
}
