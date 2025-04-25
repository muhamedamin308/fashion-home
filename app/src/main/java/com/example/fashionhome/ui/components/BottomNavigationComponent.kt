package com.example.fashionhome.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fashionhome.R

/**
 * @author Muhamed Amin Hassan on 24,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

@Composable
private fun BottomMenuItemComponent(
    icon: Painter,
    text: String,
    onItemClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .height(70.dp)
            .clickable {
                onItemClick?.invoke()
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = text, tint = Color.White)
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Text(
            text,
            color = Color.White,
            fontSize = 10.sp
        )
    }
}

@Composable
fun BottomNavigationComponent(
    modifier: Modifier,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
            .background(
                color = colorResource(R.color.dark_brown),
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        BottomMenuItemComponent(
            icon = painterResource(R.drawable.btn_1),
            text = "Explore"
        )
        BottomMenuItemComponent(
            icon = painterResource(R.drawable.btn_2),
            text = "Cart"
        ) {
            onItemClick()
        }
        BottomMenuItemComponent(
            icon = painterResource(R.drawable.btn_3),
            text = "Favourite"
        )
        BottomMenuItemComponent(
            icon = painterResource(R.drawable.btn_4),
            text = "Orders"
        )
        BottomMenuItemComponent(
            icon = painterResource(R.drawable.btn_5),
            text = "Profile"
        )
    }
}
