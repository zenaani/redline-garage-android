package com.zenaani.redlinegarage.core.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val navIcons: List<ImageVector> = listOf(
    Icons.Default.Home,
    Icons.Default.Search,
    Icons.Default.Save,
    Icons.Default.Person,
)

private val SelectedTint = Color(0xFF171719)
private val UnselectedTint = Color(0xFF929299)

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {},
) {
    // -1f = far left, 1f = far right. With 4 equal slots and a pill that is 1/4 of the
    // bar's width, this bias lands the pill exactly on top of the selected slot.
    val bias by animateFloatAsState(
        targetValue = -1f + 2f * selectedIndex / (navIcons.size - 1),
        animationSpec = tween(600),
        label = "navPillBias",
    )

    Box(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(shape = CircleShape, color = Color(0xFF202024)),
    ) {
        // Sliding white pill
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(1f / navIcons.size)
                .padding(horizontal = 8.dp)
                .background(shape = CircleShape, color = Color.White)
                .align(BiasAlignment(horizontalBias = bias, verticalBias = 0f))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            navIcons.forEachIndexed { index, icon ->
                val tint by animateColorAsState(
                    targetValue = if (index == selectedIndex) SelectedTint else UnselectedTint,
                    animationSpec = tween(durationMillis = 250),
                    label = "navIconTint$index",
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onItemSelected(index) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.height(24.dp),
                        tint = tint,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    var selected by remember { mutableIntStateOf(0) }
    NavBar(
        selectedIndex = selected,
        onItemSelected = { selected = it },
    )
}