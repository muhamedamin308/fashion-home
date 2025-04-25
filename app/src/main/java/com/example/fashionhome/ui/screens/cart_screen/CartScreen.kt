package com.example.fashionhome.ui.screens.cart_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.fashionhome.R
import com.example.fashionhome.helper.ChangeNumberItemsListener
import com.example.fashionhome.helper.ManagementCart
import com.example.fashionhome.model.Item
import kotlin.math.roundToInt

/**
 * @author Muhamed Amin Hassan on 25,April,2025
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CartScreen(
    managementCart: ManagementCart = ManagementCart(LocalContext.current),
    onBackClick: () -> Unit,
) {
    var cartItems = remember { mutableStateOf(managementCart.getListCart()) }
    val tax = remember { mutableDoubleStateOf(0.0) }
    calculatorCart(managementCart, tax)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            ConstraintLayout(modifier = Modifier.padding(top = 50.dp)) {
                val (backButton, cartText) = createRefs()
                Text(
                    text = "Your Cart",
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(cartText) {
                            centerTo(parent)
                        },
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable(onClick = onBackClick)
                        .constrainAs(backButton) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                )
            }
        }

        if (cartItems.value.isEmpty()) {
            item {
                Text(
                    text = "Your cart is empty",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(
                items = cartItems.value,
                key = { it.hashCode() } // or any unique property of Item
            ) { item ->
                CartItemComponent(
                    list = cartItems.value,
                    item = item,
                    managementCart = managementCart,
                    onItemChange = {
                        cartItems.value = ArrayList(managementCart.getListCart())
                        calculatorCart(managementCart, tax)
                    }
                )
            }

            item {
                CartSummaryComponent(
                    itemTotal = managementCart.getTotalFee(),
                    tax = tax.doubleValue,
                    delivery = 10.0
                )
            }
        }
    }
}


fun calculatorCart(
    managementCart: ManagementCart,
    tax: MutableState<Double>,
) {
    val percentTax = 0.02
    tax.value = ((managementCart.getTotalFee() * percentTax) * 100).roundToInt() / 100.0
}

@Composable
fun CartSummaryComponent(
    itemTotal: Double,
    tax: Double,
    delivery: Double,
) {
    val total = itemTotal + tax + delivery

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Item Total: ",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.dark_brown),
                modifier = Modifier.weight(1f)
            )
            Text(text = "$$itemTotal")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Item Tax: ",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.dark_brown),
                modifier = Modifier.weight(1f)
            )
            Text(text = "$$tax")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Item Delivery: ",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.dark_brown),
                modifier = Modifier.weight(1f)
            )
            Text(text = "$$delivery")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total: ",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.dark_brown),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$$total",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.dark_brown)
            )
        }
        Button(
            onClick = { },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.dark_brown)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Check Out",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CartItemComponent(
    list: ArrayList<Item>,
    item: Item,
    managementCart: ManagementCart,
    onItemChange: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        val (
            picture,
            title,
            feeEachItem,
            totalEachItem,
            quantity,
        ) = createRefs()
        Image(
            painter = rememberAsyncImagePainter(item.picUrl[0]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .background(
                    color = colorResource(R.color.very_light_brown),
                    shape = RoundedCornerShape(10.dp)
                )
                .constrainAs(picture) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = item.title,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(picture.end)
                    top.linkTo(picture.top)
                }
                .padding(start = 8.dp, top = 8.dp)
        )
        Text(
            text = "$${item.price}",
            modifier = Modifier
                .constrainAs(feeEachItem) {
                    start.linkTo(title.end)
                    top.linkTo(title.bottom)
                }
                .padding(start = 8.dp, top = 8.dp),
            color = colorResource(R.color.dark_brown)
        )

        Text(
            text = "$${item.numberInCart * item.price}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(totalEachItem) {
                    start.linkTo(title.start)
                    top.linkTo(title.bottom)
                }
                .padding(start = 8.dp, top = 8.dp),
            color = colorResource(R.color.dark_brown)
        )

        ConstraintLayout(
            modifier = Modifier
                .width(100.dp)
                .constrainAs(quantity) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .background(
                    color = colorResource(R.color.light_brown),
                    shape = RoundedCornerShape(100.dp)
                )
        ) {
            val (
                plusCartButton,
                minusCartButton,
                quantityText,
            ) = createRefs()
            Text(
                text = item.numberInCart.toString(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(quantityText) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(25.dp)
                    .background(
                        colorResource(R.color.dark_brown),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .constrainAs(plusCartButton) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        managementCart.plusItem(
                            list.indexOf(item),
                            object : ChangeNumberItemsListener {
                                override fun onChange() {
                                    onItemChange()
                                }
                            }
                        )
                    }
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(25.dp)
                    .background(
                        colorResource(R.color.white),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .constrainAs(minusCartButton) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        managementCart.minusItem(
                            list.indexOf(item),
                            object : ChangeNumberItemsListener {
                                override fun onChange() {
                                    onItemChange()
                                }
                            }
                        )
                    }
            ) {
                Text(
                    text = "-",
                    color = colorResource(R.color.dark_brown),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}