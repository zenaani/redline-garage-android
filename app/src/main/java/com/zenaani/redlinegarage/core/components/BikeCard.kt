package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathSegment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.R
import com.zenaani.redlinegarage.ui.theme.BodoniModa

@Composable
fun BikeCard(modifier: Modifier = Modifier, brand: String, model: String, segment: String, variant: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
    ) {
        Row() {
            Image(
                modifier = Modifier
                    .height(80.dp)
                    .width(160.dp),
                painter = painterResource(R.drawable.honda_cb300r),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    brand,
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraLight,
                    letterSpacing = 3.sp
                )
                Spacer(modifier.height(2.dp))
                Text(
                    model,
                    fontSize = 16.sp,
                    lineHeight = 2.sp,
                    color = Color.White,
                    fontFamily = BodoniModa,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier.height(2.dp))
                Text("286cc • $segment • $variant", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

@Preview
@Composable
fun BikeCardPreview() {
    BikeCard(brand = "Honda", model = "CB300R", segment = "Naked Sport", variant = "Standard")
}