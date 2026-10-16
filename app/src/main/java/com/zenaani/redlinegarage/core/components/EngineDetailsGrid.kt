package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class EngineItemData(val title: String, val value: String)

@Composable
fun EngineSpecsGrid(
    items: List<EngineItemData>,
    modifier: Modifier = Modifier,
    columns: Int = 3,
    dividerColor: Color = Color.White.copy(alpha = 0.12f),
) {
    Column(modifier.fillMaxWidth()) {
        items.chunked(columns).forEachIndexed { rowIndex, rowItems ->
            if (rowIndex > 0) {
                HorizontalDivider(thickness = 1.dp, color = dividerColor)
            }
            Row(Modifier.height(IntrinsicSize.Min)) {
                rowItems.forEachIndexed { col, item ->
                    EngineItem(
                        engineItemData = item,
                        showStartDivider = col > 0,
                        dividerColor = dividerColor,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
                // keep columns aligned on a partial last row
                repeat(columns - rowItems.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
fun EngineItem(
    engineItemData: EngineItemData,
    showStartDivider: Boolean,
    dividerColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .drawBehind {
                if (showStartDivider) {
                    val stroke = 1.dp.toPx()
                    val inset = 10.dp.toPx()
                    drawLine(
                        color = dividerColor,
                        start = Offset(stroke / 2, inset),
                        end = Offset(stroke / 2, size.height - inset),
                        strokeWidth = stroke
                    )
                }
            }
            .padding(
                start = if (showStartDivider) 16.dp else 0.dp,
                top = 18.dp,
                bottom = 18.dp
            )
    ) {
        Text(
            engineItemData.title,
            fontSize = 10.sp,
            color = Color.Gray,
            fontWeight = FontWeight.ExtraLight
        )
        Text(
            engineItemData.value,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraLight
        )
    }
}