package com.zenaani.redlinegarage.core.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.layout.onSizeChanged
import kotlin.math.hypot

@Composable
fun SearchReveal(
    isSearchOpen: Boolean,
    onSearchClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var screenSize by remember { mutableStateOf(IntSize.Zero) }
    val radius = remember { Animatable(0f) }

    LaunchedEffect(isSearchOpen, screenSize) {
        if (screenSize == IntSize.Zero) return@LaunchedEffect

        val maxRadius = hypot(
            screenSize.width.toFloat(),
            screenSize.height.toFloat()
        )

        radius.animateTo(
            targetValue = if (isSearchOpen) maxRadius * 1.5f else 0f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            )
        )
    }

    BackHandler(enabled = isSearchOpen) {
        onSearchClose()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { screenSize = it }
    ) {
        // Your existing screen content goes here.

        if (radius.value > 0f || isSearchOpen) {
            Canvas(Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Black,
                            Color.Transparent,
                        ),
                        center = Offset(size.width, 0f),
                        radius = radius.value.coerceAtLeast(1f)
                    ),
                    radius = radius.value,
                    center = Offset(size.width, 0f)
                )
            }
        }
    }
}
