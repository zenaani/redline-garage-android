package com.zenaani.redlinegarage.core.components.performance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

// ---------------------------------------------------------------------------
// Acceleration
// ---------------------------------------------------------------------------

@Immutable
data class AccelerationRun(val label: String, val seconds: Float)

/**
 * Left: 0–X times as a divided list. Right: arc gauge highlighting [featured].
 * [gaugeProgress] is the fraction (0..1) of the arc to light up.
 */
@Composable
fun AccelerationCard(
    runs: List<AccelerationRun>,
    modifier: Modifier = Modifier,
    featured: AccelerationRun = runs.first(),
    gaugeProgress: Float = 0.2f,
) {
    PerformanceCard(title = "Acceleration", modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MetricList(
                items = runs.map { MetricItem(it.label, it.seconds.oneDecimal(), "s") },
                modifier = Modifier.weight(1.3f),
            )
            Spacer(Modifier.width(24.dp))
            SegmentedArcGauge(progress = gaugeProgress, modifier = Modifier.weight(1f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ValueWithUnit(
                        value = featured.seconds.oneDecimal(),
                        unit = "s",
                        valueStyle = PerfType.GaugeValue,
                        unitStyle = PerfType.GaugeUnit,
                    )
                    Text(featured.label, style = PerfType.Caption)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Power & Torque
// ---------------------------------------------------------------------------

val DefaultRpmAxis = ChartAxis(min = 1800f, max = 10200f, step = 2000f) { "${(it / 1000).toInt()}k" }

/**
 * Legend on top, chart on the left, peak stats on the right.
 * Peaks default to the highest point of each series; pass them explicitly if your
 * dyno reports them separately.
 */
@Composable
fun PowerTorqueCard(
    power: ChartSeries,
    torque: ChartSeries,
    modifier: Modifier = Modifier,
    peakPower: ChartPoint = power.peak(),
    peakTorque: ChartPoint = torque.peak(),
    powerUnit: String = "hp",
    torqueUnit: String = "Nm",
    xAxis: ChartAxis = DefaultRpmAxis,
    yAxis: ChartAxis = ChartAxis(min = 0f, max = 40f, step = 10f),
) {
    PerformanceCard(title = "Power & Torque", modifier = modifier) {
        ChartLegend(listOf(power, torque))
        Spacer(Modifier.height(16.dp))
        Row {
            PerformanceLineChart(
                series = listOf(power, torque),
                xAxis = xAxis,
                yAxis = yAxis,
                xAxisTitle = "RPM",
                contentDescription = "Power and torque curves by RPM",
                modifier = Modifier.weight(1f).height(240.dp),
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.width(IntrinsicSize.Max)) {
                StatBlock(
                    label = "Power",
                    value = peakPower.y.oneDecimal(),
                    unit = powerUnit,
                    caption = "@ ${peakPower.x.rpm()} rpm",
                )
                HairlineDivider(Modifier.padding(vertical = 16.dp))
                StatBlock(
                    label = "Torque",
                    value = peakTorque.y.oneDecimal(),
                    unit = torqueUnit,
                    caption = "@ ${peakTorque.x.rpm()} rpm",
                )
            }
        }
    }
}

private fun Float.oneDecimal(): String = "%.1f".format(this)
private fun Float.rpm(): String = "%,d".format(roundToInt())

// ---------------------------------------------------------------------------
// Preview / sample data (matches the mock)
// ---------------------------------------------------------------------------

fun pts(vararg p: Pair<Float, Float>) = p.map { ChartPoint(it.first, it.second) }

@Preview(showBackground = true, backgroundColor = 0xFF07090C, widthDp = 400)
@Composable
private fun PerformanceCardsPreview() {
    val power = ChartSeries(
        label = "Power (hp)",
        color = PerfColors.Power,
        points = pts(
            1800f to 0.5f, 2000f to 0.8f, 3000f to 4.3f, 4000f to 9.3f, 5000f to 14.5f,
            6000f to 19.8f, 7000f to 25.0f, 8000f to 28.8f, 9000f to 31.1f,
            9600f to 30.4f, 10200f to 28.5f,
        ),
    )
    val torque = ChartSeries(
        label = "Torque (Nm)",
        color = PerfColors.Torque,
        points = pts(
            1800f to 2.5f, 2500f to 5.8f, 3000f to 9.0f, 4000f to 16.2f, 5000f to 20.8f,
            6000f to 22.5f, 6500f to 22.8f, 7000f to 22.6f, 8000f to 21.2f,
            9000f to 18.8f, 10000f to 16.0f, 10200f to 15.2f,
        ),
    )

    Column(
        modifier = Modifier.background(PerfColors.Background).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AccelerationCard(
            runs = listOf(
                AccelerationRun("0 – 60 km/h", 5.7f),
                AccelerationRun("0 – 100 km/h", 13.8f),
                AccelerationRun("0 – 140 km/h", 29.4f),
            ),
        )
        PowerTorqueCard(
            power = power,
            torque = torque,
            peakPower = ChartPoint(9000f, 31.1f),
            peakTorque = ChartPoint(7750f, 27.5f),
        )
    }
}