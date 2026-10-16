package com.zenaani.redlinegarage.core.components.performance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.floor

@Immutable
data class ChartPoint(val x: Float, val y: Float)

@Immutable
data class ChartSeries(
    val label: String,
    val color: Color,
    val points: List<ChartPoint>,
) {
    /** Highest point of the series (throws if empty). */
    fun peak(): ChartPoint = points.maxBy { it.y }
}

/** Axis range plus tick spacing. Ticks start at the first multiple of [step] >= [min]. */
@Immutable
class ChartAxis(
    val min: Float,
    val max: Float,
    val step: Float,
    val label: (Float) -> String = { it.toInt().toString() },
)

/** Line swatch + label for each series, laid out in a row. */
@Composable
fun ChartLegend(
    series: List<ChartSeries>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        series.forEach { s ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.width(16.dp).height(2.dp).background(s.color))
                Spacer(Modifier.width(8.dp))
                Text(s.label, style = PerfType.Legend)
            }
        }
    }
}

/**
 * Multi-series line chart with grid, tick labels and an optional x-axis title.
 * Size it from the outside (e.g. `Modifier.height(240.dp)`).
 */
@Composable
fun PerformanceLineChart(
    series: List<ChartSeries>,
    xAxis: ChartAxis,
    yAxis: ChartAxis,
    modifier: Modifier = Modifier,
    xAxisTitle: String? = null,
    lineWidth: Dp = 2.dp,
    smooth: Boolean = true,
    gridColor: Color = PerfColors.Grid,
    labelStyle: TextStyle = PerfType.AxisLabel,
    contentDescription: String? = null,
) {
    val measurer = rememberTextMeasurer()
    val a11y = if (contentDescription != null) {
        Modifier.semantics { this.contentDescription = contentDescription }
    } else {
        Modifier
    }

    Canvas(modifier.then(a11y)) {
        val xTicks = xAxis.ticks()
        val yTicks = yAxis.ticks()
        val xLabels = xTicks.map { measurer.measure(xAxis.label(it), labelStyle) }
        val yLabels = yTicks.map { measurer.measure(yAxis.label(it), labelStyle) }
        val title = xAxisTitle?.let { measurer.measure(it, labelStyle) }

        // Plot rect: leave room for tick labels (left/bottom) and the axis title.
        val gap = 8.dp.toPx()
        val plotLeft = (yLabels.maxOfOrNull { it.size.width } ?: 0) + gap
        val plotTop = (yLabels.lastOrNull()?.size?.height ?: 0) / 2f
        val xLabelHeight = xLabels.maxOfOrNull { it.size.height } ?: 0
        val titleBlock = title?.let { it.size.height + gap } ?: 0f
        val plotBottom = size.height - xLabelHeight - gap - titleBlock
        val plotRight = size.width - (xLabels.lastOrNull()?.size?.width ?: 0) / 2f
        val plot = Rect(plotLeft, plotTop, plotRight, plotBottom)
        if (plot.width <= 0f || plot.height <= 0f) return@Canvas

        val xSpan = xAxis.max - xAxis.min
        val ySpan = yAxis.max - yAxis.min
        fun px(x: Float) = plot.left + (x - xAxis.min) / xSpan * plot.width
        fun py(y: Float) = plot.bottom - (y - yAxis.min) / ySpan * plot.height

        // Grid + frame
        val hairline = 1.dp.toPx()
        yTicks.forEach {
            drawLine(gridColor, Offset(plot.left, py(it)), Offset(plot.right, py(it)), hairline)
        }
        xTicks.forEach {
            drawLine(gridColor, Offset(px(it), plot.top), Offset(px(it), plot.bottom), hairline)
        }
        drawRect(gridColor, plot.topLeft, plot.size, style = Stroke(hairline))

        // Tick labels
        yTicks.forEachIndexed { i, v ->
            val l = yLabels[i]
            drawText(l, topLeft = Offset(plot.left - gap - l.size.width, py(v) - l.size.height / 2f))
        }
        xTicks.forEachIndexed { i, v ->
            val l = xLabels[i]
            val x = (px(v) - l.size.width / 2f).coerceIn(0f, maxOf(0f, size.width - l.size.width))
            drawText(l, topLeft = Offset(x, plot.bottom + gap))
        }
        title?.let {
            drawText(it, topLeft = Offset(plot.center.x - it.size.width / 2f, size.height - it.size.height))
        }

        // Series (clip with a little slack so end caps / edge strokes aren't shaved off)
        val slack = lineWidth.toPx()
        clipRect(plot.left - slack, plot.top - slack, plot.right + slack, plot.bottom + slack) {
            series.forEach { s ->
                val pts = s.points.map { Offset(px(it.x), py(it.y)) }
                drawPath(
                    path = if (smooth) smoothPath(pts) else linearPath(pts),
                    color = s.color,
                    style = Stroke(width = lineWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
                )
            }
        }
    }
}

private fun ChartAxis.ticks(): List<Float> {
    val first = ceil(min / step) * step
    val count = floor((max - first) / step + 1e-4f).toInt()
    return (0..count).map { first + it * step }
}

private fun linearPath(pts: List<Offset>): Path = Path().apply {
    pts.forEachIndexed { i, p -> if (i == 0) moveTo(p.x, p.y) else lineTo(p.x, p.y) }
}

/** Catmull-Rom spline converted to cubic Béziers. */
private fun smoothPath(pts: List<Offset>): Path {
    val path = Path()
    if (pts.isEmpty()) return path
    path.moveTo(pts[0].x, pts[0].y)
    for (i in 0 until pts.size - 1) {
        val p0 = pts[(i - 1).coerceAtLeast(0)]
        val p1 = pts[i]
        val p2 = pts[i + 1]
        val p3 = pts[(i + 2).coerceAtMost(pts.size - 1)]
        path.cubicTo(
            p1.x + (p2.x - p0.x) / 6f, p1.y + (p2.y - p0.y) / 6f,
            p2.x - (p3.x - p1.x) / 6f, p2.y - (p3.y - p1.y) / 6f,
            p2.x, p2.y,
        )
    }
    return path
}