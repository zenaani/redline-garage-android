package com.zenaani.redlinegarage.core.components.performance

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Open-bottom arc split into [segments] with small gaps. [progress] (0..1) lights up
 * segments from the start; the segment in progress is filled partially.
 *
 * Angles follow Canvas convention: 0° = 3 o'clock, clockwise.
 * Default (150° start, 240° sweep) runs lower-left → over the top → lower-right.
 *
 * [content] is drawn centred inside the arc.
 */
@Composable
fun SegmentedArcGauge(
    progress: Float,
    modifier: Modifier = Modifier,
    segments: Int = 5,
    startAngle: Float = 150f,
    sweepAngle: Float = 240f,
    gapAngle: Float = 3f,
    strokeWidth: Dp = 8.dp,
    trackColor: Color = PerfColors.GaugeTrack,
    progressColor: Color = PerfColors.GaugeActive,
    animationMillis: Int = 900,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val animated = remember { Animatable(0f) }
    LaunchedEffect(progress) {
        animated.animateTo(
            targetValue = progress.coerceIn(0f, 1f),
            animationSpec = tween(animationMillis, easing = FastOutSlowInEasing),
        )
    }

    Box(modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
            val diameter = size.minDimension - stroke.width
            val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
            val arcSize = Size(diameter, diameter)

            val segSweep = (sweepAngle - gapAngle * (segments - 1)) / segments
            val filledSegments = animated.value * segments

            repeat(segments) { i ->
                val segStart = startAngle + i * (segSweep + gapAngle)

                drawArc(
                    color = trackColor,
                    startAngle = segStart,
                    sweepAngle = segSweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = stroke,
                )

                val fraction = (filledSegments - i).coerceIn(0f, 1f)
                if (fraction > 0f) {
                    drawArc(
                        color = progressColor,
                        startAngle = segStart,
                        sweepAngle = segSweep * fraction,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = stroke,
                    )
                }
            }
        }
        content()
    }
}