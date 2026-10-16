package com.zenaani.redlinegarage.core.components.performance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Immutable
data class MetricItem(val label: String, val value: String, val unit: String? = null)

/** Big number followed by a smaller unit, baseline-aligned in a single Text. */
@Composable
fun ValueWithUnit(
    value: String,
    unit: String?,
    valueStyle: TextStyle,
    unitStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Text(
        text = buildAnnotatedString {
            append(value)
            if (unit != null) {
                withStyle(unitStyle.toSpanStyle()) { append(" $unit") }
            }
        },
        style = valueStyle,
        modifier = modifier,
    )
}

@Composable
fun HairlineDivider(
    modifier: Modifier = Modifier,
    color: Color = PerfColors.Divider,
) {
    Box(modifier.fillMaxWidth().height(1.dp).background(color))
}

/** Label on the left, value + unit on the right. */
@Composable
fun MetricRow(item: MetricItem, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(item.label, style = PerfType.RowLabel, modifier = Modifier.weight(1f))
        ValueWithUnit(item.value, item.unit, PerfType.RowValue, PerfType.RowUnit)
    }
}

/** Stack of [MetricRow]s separated by hairline dividers. */
@Composable
fun MetricList(items: List<MetricItem>, modifier: Modifier = Modifier) {
    Column(modifier) {
        items.forEachIndexed { index, item ->
            if (index > 0) HairlineDivider()
            MetricRow(item)
        }
    }
}

/** LABEL / big value + unit / caption — e.g. POWER, 31.1 hp, @ 9,000 rpm. */
@Composable
fun StatBlock(
    label: String,
    value: String,
    unit: String,
    caption: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(label.uppercase(), style = PerfType.StatLabel)
        Spacer(Modifier.height(6.dp))
        ValueWithUnit(value, unit, PerfType.StatValue, PerfType.StatValue)
        Spacer(Modifier.height(2.dp))
        Text(caption, style = PerfType.Caption)
    }
}