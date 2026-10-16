package com.zenaani.redlinegarage.core.components

import com.zenaani.redlinegarage.core.components.performance.PerfColors
import com.zenaani.redlinegarage.core.components.performance.PerfType
import com.zenaani.redlinegarage.core.components.performance.PerformanceCard
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ---------------------------------------------------------------------------
// Data
// ---------------------------------------------------------------------------

@Immutable
data class SpecItem(val label: String, val value: String)

// ---------------------------------------------------------------------------
// SpecGrid — N-column grid with vertical dividers between columns and
// hairline dividers between rows. Works standalone (e.g. engine specs) or
// inside [SpecCard].
// ---------------------------------------------------------------------------

@Composable
fun SpecGrid(
    items: List<SpecItem>,
    modifier: Modifier = Modifier,
    columns: Int = 3,
    labelStyle: TextStyle = PerfType.Caption,
    valueStyle: TextStyle = PerfType.RowValue,
    dividerColor: Color = PerfColors.Divider,
) {
    val rows = items.chunked(columns)
    Column(modifier.fillMaxWidth()) {
        rows.forEachIndexed { rowIndex, rowItems ->
            if (rowIndex > 0) HorizontalDivider(thickness = 1.dp, color = dividerColor)
            Row(
                Modifier
                    .padding(
                        top = if (rowIndex > 0) 12.dp else 0.dp,
                        bottom = if (rowIndex < rows.lastIndex) 12.dp else 0.dp,
                    )
                    // equal-height cells so every vertical divider in a row has the same length
                    .height(IntrinsicSize.Min),
            ) {
                rowItems.forEachIndexed { col, item ->
                    SpecCell(
                        item = item,
                        showStartDivider = col > 0,
                        labelStyle = labelStyle,
                        valueStyle = valueStyle,
                        dividerColor = dividerColor,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                }
                // keep column widths aligned on a partial last row
                repeat(columns - rowItems.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun SpecCell(
    item: SpecItem,
    showStartDivider: Boolean,
    labelStyle: TextStyle,
    valueStyle: TextStyle,
    dividerColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .drawBehind {
                if (showStartDivider) {
                    val stroke = 1.dp.toPx()
                    drawLine(
                        color = dividerColor,
                        start = Offset(stroke / 2f, 0f),
                        end = Offset(stroke / 2f, size.height),
                        strokeWidth = stroke,
                    )
                }
            }
            .padding(start = if (showStartDivider) 14.dp else 0.dp, top = 4.dp, bottom = 4.dp),
    ) {
        Text(item.label, style = labelStyle)
        Spacer(Modifier.height(4.dp))
        Text(item.value, style = valueStyle)
    }
}

// ---------------------------------------------------------------------------
// SpecCard — PerformanceCard + optional icon header + SpecGrid
// ---------------------------------------------------------------------------

@Composable
fun SpecCard(
    title: String,
    items: List<SpecItem>,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    columns: Int = 3,
) {
    PerformanceCard(
        title = title,
        modifier = modifier,
        icon = if (icon != null) ({ IconBadge(icon) }) else null,
        titleStyle = PerfType.CardTitleLarge,
    ) {
        SpecGrid(items = items, columns = columns)
    }
}

/** Outlined rounded square with a centred icon, used in card headers. */
@Composable
fun IconBadge(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = PerfColors.TextLabel,
    borderColor: Color = PerfColors.TextSecondary.copy(alpha = 0.6f),
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(18.dp),
        )
    }
}

/** Four outward corner arrows. Stroke-only, so it takes the Icon tint. */
val DimensionsIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "Dimensions",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.6f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
        ) {
            // top-left
            moveTo(4f, 9f); verticalLineTo(4f); horizontalLineTo(9f)
            moveTo(4f, 4f); lineTo(9.5f, 9.5f)
            // top-right
            moveTo(15f, 4f); horizontalLineTo(20f); verticalLineTo(9f)
            moveTo(20f, 4f); lineTo(14.5f, 9.5f)
            // bottom-left
            moveTo(4f, 15f); verticalLineTo(20f); horizontalLineTo(9f)
            moveTo(4f, 20f); lineTo(9.5f, 14.5f)
            // bottom-right
            moveTo(20f, 15f); verticalLineTo(20f); horizontalLineTo(15f)
            moveTo(20f, 20f); lineTo(14.5f, 14.5f)
        }
    }.build()
}

// ---------------------------------------------------------------------------
// Preview (matches the mock)
// ---------------------------------------------------------------------------

@Preview(showBackground = true, backgroundColor = 0xFF07090C, widthDp = 400)
@Composable
private fun SpecCardPreview() {
    Column(Modifier
        .background(PerfColors.Background)
        .padding(16.dp)) {
        SpecCard(
            title = "Dimensions & Weight",
            icon = DimensionsIcon,
            items = listOf(
                SpecItem("Overall Length", "2,080 mm"),
                SpecItem("Overall Width", "805 mm"),
                SpecItem("Overall Height", "1,055 mm"),
                SpecItem("Wheelbase", "1,406 mm"),
                SpecItem("Seat Height", "799 mm"),
                SpecItem("Kerb Weight", "146 kg"),
            ),
        )
    }
}