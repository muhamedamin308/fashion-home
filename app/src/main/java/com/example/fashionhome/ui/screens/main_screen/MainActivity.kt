package com.example.fashionhome.ui.screens.main_screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fashionhome.R
import com.example.fashionhome.model.Category
import com.example.fashionhome.model.Item
import com.example.fashionhome.model.SliderModel
import com.example.fashionhome.ui.screens.BaseActivity
import com.example.fashionhome.ui.screens.cart_screen.CartActivity
import com.example.fashionhome.viewmodel.MainViewModel

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen {
                startActivity(
                    Intent(
                        this,
                        CartActivity::class.java
                    )
                )
            }
        }
    }
}

@Composable
fun MainScreen(onCartClicked: () -> Unit) {

    val viewModel = MainViewModel()
    val banners = remember { mutableStateListOf<SliderModel>() }
    val categories = remember { mutableStateListOf<Category>() }
    val popularProducts = remember { mutableStateListOf<Item>() }

    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }
    var showPopularProductsLoading by remember { mutableStateOf(true) }

    // banner launch
    LaunchedEffect(Unit) {
        viewModel.loadBanner().observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading = false
        }
    }

    // category launch
    LaunchedEffect(Unit) {
        viewModel.loadCategories().observeForever {
            categories.clear()
            categories.addAll(it)
            showCategoryLoading = false
        }
    }

    // popular products launch
    LaunchedEffect(Unit) {
        viewModel.loadPopularProducts().observeForever {
            popularProducts.clear()
            popularProducts.addAll(it)
            showPopularProductsLoading = false
        }
    }

    MainBodyComponent(
        banners = banners,
        categories = categories,
        products = popularProducts,
        showBannerLoading = showBannerLoading,
        showCategoryLoading = showCategoryLoading,
        showProductsLoading = showPopularProductsLoading,
        onCartItemClicked = onCartClicked
    )
}

@Composable
fun SectionTitleComponent(title: String, actionTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = actionTitle,
            color = colorResource(R.color.dark_brown),
        )
    }
}
