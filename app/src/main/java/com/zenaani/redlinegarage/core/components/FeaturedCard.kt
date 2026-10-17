package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.R
import com.zenaani.redlinegarage.ui.theme.BodoniModa


@Composable
fun FeaturedCard(modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.Black, RoundedCornerShape(4.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.honda_cb300r),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.75f)
                .align(
                    Alignment.CenterEnd
                )
        )

        // Gradient Overlay (left -> right)
        Box(
            modifier = Modifier
                .fillMaxHeight().fillMaxWidth(0.75f)
                .align(Alignment.CenterEnd)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black, Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                "FEATURED",
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraLight,
                letterSpacing = 2.sp
            )
            Text(
                "Performance \nthat speaks",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = BodoniModa,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier.height(6.dp))
            Text(
                "Power, torque, acceleration and more.",
                fontSize = 8.sp,
                color = Color.Gray,
                fontWeight = FontWeight.ExtraLight,
                letterSpacing = 1.sp
            )

            Spacer(modifier.height(6.dp))
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.Gray)) {
                        append("SEE MORE ")
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

@Preview
@Composable
fun FeaturedCardPreview() {
    FeaturedCard()
}