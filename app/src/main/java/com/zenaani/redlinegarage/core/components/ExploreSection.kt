package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.ui.theme.BodoniModa

@Composable
fun ExploreSection(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "EXPLORE MOTORCYCLES",
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = BodoniModa,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "View all →",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraLight,
            letterSpacing = 1.sp
        )
    }

    Spacer(Modifier.height(12.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(5) {
            ExploreCard()
        }
    }

}