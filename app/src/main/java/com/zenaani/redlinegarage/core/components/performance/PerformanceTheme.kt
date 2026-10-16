package com.zenaani.redlinegarage.core.components.performance

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Design tokens for the performance dashboard. */
object PerfColors {
    val Background = Color(0xFF07090C)
    val Card = Color(0xFF0D1014)
    val CardBorder = Color(0xFF1E2228)
    val Divider = Color(0xFF22262C)
    val Grid = Color(0xFF1B1F25)

    val TextPrimary = Color(0xFFF2F2F2)
    val TextSecondary = Color(0xFFA7ABB1)
    val TextLabel = Color(0xFFD9DADC)

    val GaugeTrack = Color(0xFF23272D)
    val GaugeActive = Color(0xFFDADADA)

    val Power = Color(0xFFE5392B)
    val Torque = Color(0xFFEDEDED)
}

object PerfType {
    /** Swap for a bundled serif (Newsreader / Source Serif) to match the mock more closely. */
    val ValueFont: FontFamily = FontFamily.Serif

    val SectionLabel = TextStyle(
        color = PerfColors.TextLabel,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 2.5.sp,
    )
    val StatLabel = SectionLabel.copy(fontSize = 11.sp, letterSpacing = 2.sp)

    val RowLabel = TextStyle(color = PerfColors.TextSecondary, fontSize = 15.sp)
    val RowValue = TextStyle(color = PerfColors.TextPrimary, fontFamily = ValueFont, fontSize = 22.sp)
    val RowUnit = TextStyle(color = PerfColors.TextPrimary, fontFamily = ValueFont, fontSize = 15.sp)

    val GaugeValue = TextStyle(color = PerfColors.TextPrimary, fontFamily = ValueFont, fontSize = 40.sp)
    val GaugeUnit = TextStyle(color = PerfColors.TextPrimary, fontFamily = ValueFont, fontSize = 22.sp)

    val StatValue = TextStyle(color = PerfColors.TextPrimary, fontFamily = ValueFont, fontSize = 24.sp)

    val Caption = TextStyle(color = PerfColors.TextSecondary, fontSize = 13.sp)
    val Legend = TextStyle(color = PerfColors.TextSecondary, fontSize = 13.sp)
    val AxisLabel = TextStyle(color = PerfColors.TextSecondary, fontSize = 11.sp)
}

/** Rounded dark card with an uppercase, letter-spaced title. */
@Composable
fun PerformanceCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = PerfColors.Card,
        border = BorderStroke(1.dp, PerfColors.CardBorder),
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(text = title.uppercase(), style = PerfType.SectionLabel)
            Spacer(Modifier.height(20.dp))
            content()
        }
    }
}