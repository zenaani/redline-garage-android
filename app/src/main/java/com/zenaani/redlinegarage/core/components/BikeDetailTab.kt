package com.zenaani.redlinegarage.core.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BikeDetailTab(
    selectedTab: BikeDetailTab,
    onTabSelected: (BikeDetailTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = BikeDetailTab.entries
    val selectedIndex = tabs.indexOf(selectedTab).coerceAtLeast(0)

    val density = LocalDensity.current
    var rowWidth by remember { mutableIntStateOf(0) }

    val tabWidthPx = rowWidth / tabs.size
    val animatedOffset by animateIntAsState(
        targetValue = selectedIndex * tabWidthPx,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "underlineOffset"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { rowWidth = it.width }
        ) {
            tabs.forEach { tab ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTabSelected(tab) }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.text,
                        fontSize = 8.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraLight,
                        letterSpacing = 3.sp
                    )
                }
            }
        }

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val tabWidth = maxWidth / tabs.size
            val underlineWidth = tabWidth * 0.6f

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = animatedOffset +
                                    ((tabWidth - underlineWidth) / 2)
                                        .roundToPx(),
                            y = 0
                        )
                    }
                    .width(underlineWidth)
                    .height(0.7.dp)
                    .background(Color.White)
            )
        }
    }
}

enum class BikeDetailTab(val text: String) {
    OVERVIEW("OVERVIEW"),
    PERFORMANCE("PERFORMANCE"),
    SPECS("SPECS"),
    ACCESSORIES("ACCESSORIES")
}


@Preview
@Composable
fun BikeDetailsTabPreview() {
    BikeDetailTab(BikeDetailTab.PERFORMANCE, onTabSelected = { })
}

