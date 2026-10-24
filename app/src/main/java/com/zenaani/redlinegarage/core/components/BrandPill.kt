package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.zenaani.redlinegarage.R

@Composable
fun BrandPill(
    modifier: Modifier = Modifier,
    name: String = "HONDA",
    logo: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.DarkGray)
            .size(64.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,

        ) {
//        Image(
//            modifier = Modifier
//                .size(24.dp)
//                .clip(CircleShape),
//            painter = painterResource(logo),
//            contentScale = ContentScale.Crop,
//            contentDescription = null
//        )

        AsyncImage(
            model = logo,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentDescription = null
        )

//        Text(
//            name,
//            fontSize = 10.sp,
//            color = Color.White,
//            letterSpacing = 2.sp
//        )

    }
}

@Preview
@Composable
fun BrandPillPreview() {
    BrandPill(logo = "", onClick = { })
}