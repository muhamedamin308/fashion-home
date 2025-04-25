package com.example.fashionhome.ui.screens.main_screen

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.fashionhome.R
import com.example.fashionhome.model.Category
import com.example.fashionhome.ui.screens.list_item_screen.ListItemActivity

/**
 * @author Muhamed Amin Hassan on 24,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

@Composable
private fun CategoryItemComponent(
    category: Category,
    isSelected: Boolean,
    onItemClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onItemClicked),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = (category.picUrl),
            contentDescription = category.title,
            modifier = Modifier
                .size(if (isSelected) 60.dp else 50.dp)
                .background(
                    color =
                        if (isSelected) colorResource(R.color.dark_brown)
                        else colorResource(R.color.very_light_brown),
                    shape = RoundedCornerShape(100.dp)
                ),
            contentScale = ContentScale.Inside,
            colorFilter = if (isSelected) {
                ColorFilter.tint(Color.White)
            } else {
                ColorFilter.tint(Color.Black)
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = category.title,
            color = colorResource(R.color.dark_brown),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun CategoryComponent(categories: List<Category>) {
    var selectedIndex by remember { mutableIntStateOf(-1) }
    val context = LocalContext.current

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {
        items(categories.size) { index ->
            CategoryItemComponent(
                category = categories[index],
                isSelected = selectedIndex == index,
                onItemClicked = {
                    selectedIndex = index
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(
                            context,
                            ListItemActivity::class.java
                        ).apply {
                            putExtra("id", categories[index].id.toString())
                            putExtra("title", categories[index].title)
                        }
                        startActivity(context, intent, null)
                    }, 500L)
                }
            )
        }
    }
}