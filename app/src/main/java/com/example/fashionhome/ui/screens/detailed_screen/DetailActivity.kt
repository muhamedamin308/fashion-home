package com.example.fashionhome.ui.screens.detailed_screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.fashionhome.helper.ManagementCart
import com.example.fashionhome.model.Item
import com.example.fashionhome.ui.screens.BaseActivity
import com.example.fashionhome.ui.screens.cart_screen.CartActivity

class DetailActivity : BaseActivity() {
    private lateinit var item: Item
    private lateinit var managementCart: ManagementCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = intent.getSerializableExtra("product") as Item
        managementCart = ManagementCart(this)

        setContent {
            DetailedScreen(
                item = item,
                onBackClick = { finish() },
                onAddToCartClick = {
                    item.numberInCart = 1
                    managementCart.insertItem(item)
                },
                onCartClick = {
                    startActivity(
                        Intent(
                            this,
                            CartActivity::class.java
                        )
                    )
                }
            )
        }
    }
}