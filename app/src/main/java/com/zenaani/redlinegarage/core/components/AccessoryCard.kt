package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.R
import com.zenaani.redlinegarage.core.components.performance.PerfColors
import com.zenaani.redlinegarage.ui.theme.BodoniModa

@Composable
fun AccessoryCard(modifier: Modifier = Modifier, name: String, price: String) {

    Card(
        modifier = modifier
            .height(200.dp)
            .border(
                width = 1.dp,
                color = PerfColors.CardBorder,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = PerfColors.Card),
        shape = RoundedCornerShape(8.dp),

        ) {
        Column() {
            Image(
                painter = painterResource(R.drawable.honda_cb300r),
                contentDescription = null,
                modifier = Modifier.height(120.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    name,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = BodoniModa,
                    fontWeight = FontWeight.Normal,
                )

                Row() {
                    Text(
                        "Rs. $price",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontFamily = BodoniModa,
                        fontWeight = FontWeight.Normal,
                    )

                }
            }

        }

    }

}