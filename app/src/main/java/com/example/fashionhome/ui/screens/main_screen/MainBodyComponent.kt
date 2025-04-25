package com.example.fashionhome.ui.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.fashionhome.R
import com.example.fashionhome.model.Category
import com.example.fashionhome.model.Item
import com.example.fashionhome.model.SliderModel
import com.example.fashionhome.ui.components.BottomNavigationComponent

/**
 * @author Muhamed Amin Hassan on 24,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

@Composable
fun MainBodyComponent(
    banners: List<SliderModel>,
    categories: List<Category>,
    products: List<Item>,
    showBannerLoading: Boolean,
    showCategoryLoading: Boolean,
    showProductsLoading: Boolean,
    onCartItemClicked: () -> Unit,
) {
    val bottomBarHeight = 80.dp

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (scrollList, bottomMenu) = createRefs()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomBarHeight) // 👈 prevent overlap
                .constrainAs(scrollList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            item { WelcomeHeader() }

            item {
                LoadingOrContent(
                    isLoading = showBannerLoading,
                    loadingHeight = 200.dp
                ) {
                    BannerComponent(banners)
                }
            }

            item {
                SectionTitleComponent("Official Products", "See All")
            }

            item {
                LoadingOrContent(
                    isLoading = showCategoryLoading,
                    loadingHeight = 50.dp
                ) {
                    CategoryComponent(categories = categories)
                }
            }

            item {
                SectionTitleComponent("Popular Products", "See All")
            }

            item {
                LoadingOrContent(
                    isLoading = showProductsLoading,
                    loadingHeight = 200.dp
                ) {
                    ListItemsComponent(items = products)
                }
            }
        }

        BottomNavigationComponent(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomMenu) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onItemClick = onCartItemClicked
        )
    }
}

@Composable
private fun WelcomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Welcome Back!", color = Color.Black)
            Text(
                text = "Muhammed!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row {
            Image(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(R.drawable.bell_icon),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun LoadingOrContent(
    isLoading: Boolean,
    loadingHeight: Dp,
    content: @Composable () -> Unit,
) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(loadingHeight),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        content()
    }
}
