package com.example.fashionhome.ui.screens.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fashionhome.R
import com.example.fashionhome.model.SliderModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

/**
 * @author Muhamed Amin Hassan on 24,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerComponent(banners: List<SliderModel>) {
    AutoSlidingCarouselComponent(
        modifier = Modifier.padding(top = 16.dp),
        banners = banners
    )
}

@Composable
private fun AutoSlidingCarouselComponent(
    modifier: Modifier = Modifier,
    pagerState: PagerState = remember { PagerState() },
    banners: List<SliderModel>,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HorizontalPager(count = banners.size, state = pagerState) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(banners[page].url)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 16.dp)
                    .height(150.dp)
            )
        }
        DotIndicatorProgressComponent(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            totalDots = banners.size,
            selectedIndex = if (isDragged) pagerState.currentPage else pagerState.currentPage,
            dotSize = 8.dp
        )
    }
}

@Composable
private fun DotIndicatorProgressComponent(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = colorResource(R.color.dark_brown),
    unSelectedColor: Color = colorResource(R.color.very_light_brown),
    dotSize: Dp,
) {
    LazyRow(
        modifier = modifier
            .wrapContentSize()
    ) {
        items(totalDots) { index ->
            IndicatorDotComponent(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}


@Composable
private fun IndicatorDotComponent(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}