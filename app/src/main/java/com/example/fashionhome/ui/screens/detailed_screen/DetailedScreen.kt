package com.example.fashionhome.ui.screens.detailed_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.fashionhome.R
import com.example.fashionhome.model.Item

@Composable
fun DetailedScreen(
    item: Item,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onCartClick: () -> Unit,
) {
    var selectedImageUrl by remember { mutableStateOf(item.picUrl.first()) }
    var selectedModeIndex by remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(430.dp)
                .padding(bottom = 16.dp)
        ) {
            val (
                back,
                favourite,
                mainImage,
                thumbnail,
            ) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = selectedImageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colorResource(R.color.very_light_brown),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .constrainAs(mainImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(top = 50.dp, start = 16.dp)
                    .clickable { onBackClick() }
                    .constrainAs(back) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
            LazyRow(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .background(
                        color = colorResource(R.color.white),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .constrainAs(thumbnail) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                items(items = item.picUrl) { imageUrl ->
                    ImageThumbnailComponent(
                        imageUrl = imageUrl,
                        isSelected = selectedImageUrl == imageUrl,
                        onClick = {
                            selectedImageUrl = imageUrl
                        }
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 16.dp)
            )
            Text(
                text = "$${item.price}",
                fontSize = 22.sp,
            )
        }
        RatingBarComponent(rating = item.rating)
        ModelSelectorComponent(
            models = item.model,
            selectedModeIndex = selectedModeIndex,
            onModeSelected = { index ->
                selectedModeIndex = index
            }
        )
        Text(
            text = item.description,
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(
                onClick = onCartClick,
                modifier = Modifier.background(
                    colorResource(R.color.very_light_brown),
                    shape = RoundedCornerShape(10.dp)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.btn_2),
                    contentDescription = "Cart",
                    tint = colorResource(R.color.dark_brown)
                )
            }
            Button(
                onClick = onAddToCartClick,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.dark_brown)
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(50.dp)
            ) {
                Text(
                    text = "Add to Cart",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ModelSelectorComponent(
    models: ArrayList<String>,
    selectedModeIndex: Int,
    onModeSelected: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        itemsIndexed(models) { index, model ->
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(40.dp)
                    .then(
                        if (index == selectedModeIndex) {
                            Modifier.border(
                                1.dp, colorResource(R.color.dark_brown),
                                shape = RoundedCornerShape(10.dp)
                            )
                        } else {
                            Modifier.border(
                                1.dp, colorResource(R.color.very_light_brown),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    )
                    .background(
                        if (index == selectedModeIndex) colorResource(R.color.dark_brown)
                        else colorResource(R.color.very_light_brown),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onModeSelected(index)
                    }
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = model,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (index == selectedModeIndex) Color.White else Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun RatingBarComponent(rating: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Selected Model",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "$rating Rating",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun ImageThumbnailComponent(
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backColor =
        if (isSelected) colorResource(R.color.dark_brown)
        else colorResource(R.color.very_light_brown)

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(55.dp)
            .then(
                if (isSelected) {
                    Modifier.border(
                        1.dp,
                        colorResource(R.color.dark_brown),
                        RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            )
            .background(color = backColor, shape = RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}