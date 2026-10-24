package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.ui.theme.BodoniModa

@Composable
fun TopBannerHome(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(com.zenaani.redlinegarage.R.drawable.honda_cb300r),
            contentDescription = null,
            modifier = Modifier.offset(x = 40.dp)
        )

        // Gradient Overlay (left -> right)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black, Color.Transparent
                        )
                    )
                )
        )

        // Gradient Overlay (bottom -> top)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color.Black
                        )
                    )
                )
        )

        // Bike Intro
        Column(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "REDLINE",
                    fontSize = 24.sp,
                    lineHeight = 2.sp,
                    color = Color.White,
                    fontFamily = BodoniModa,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
//                    modifier = Modifier.clickable(onClick = { isSearch = true })
                )


            }

            Text(
                "GARAGE",
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraLight,
                letterSpacing = 3.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "REAL BIKES. REAL NUMBERS",
                fontSize = 8.sp,
                color = Color.Gray,
                fontWeight = FontWeight.ExtraLight,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                "HONDA",
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraLight,
                letterSpacing = 3.sp
            )
            Text(
                "CB300R",
                fontSize = 34.sp,
                lineHeight = 2.sp,
                color = Color.White,
                fontFamily = BodoniModa,
                fontWeight = FontWeight.Bold
            )
            Text(
                "MINIMAL. POWERFUL. BUILT \nFOR THE EVERYDAY RIDE.",
                fontSize = 8.sp,
                color = Color.Gray,
                fontWeight = FontWeight.ExtraLight,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.Red)) {
                        append("EXPLORE DETAILS ")
                    }
                    withStyle(SpanStyle(color = Color.Gray)) {
                        append("→")
                    }
                },
                fontSize = 8.sp,
                fontWeight = FontWeight.ExtraLight,
                letterSpacing = 1.sp
            )
        }
    }


}