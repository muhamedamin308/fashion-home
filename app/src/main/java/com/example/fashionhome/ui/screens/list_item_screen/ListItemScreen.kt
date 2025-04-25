package com.example.fashionhome.ui.screens.list_item_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.fashionhome.R
import com.example.fashionhome.ui.screens.main_screen.ListItemFullSize
import com.example.fashionhome.viewmodel.MainViewModel

@Composable
fun ListItemScreen(
    title: String,
    onBackClicked: () -> Unit,
    viewModel: MainViewModel,
    id: String,
) {
    val items by viewModel.filterByCategory(id).observeAsState(emptyList())
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(id) {
        viewModel.filterByCategory(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(top = 50.dp, start = 16.dp, end = 16.dp)
        ) {
            val (backButton, cartText) = createRefs()

            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cartText) { centerTo(parent) },
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onBackClicked()
                    }
                    .constrainAs(backButton) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
            )
        }
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ListItemFullSize(items)
        }
    }
    LaunchedEffect(items) {
        isLoading = items.isEmpty()
    }
}