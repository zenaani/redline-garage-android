package com.zenaani.redlinegarage.ui.home

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onItemClick: (Int) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text("Redline Garage", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
    ) { innerPadding ->

        val uiState = viewModel.uiState.collectAsState().value

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(vertical = 24.dp), contentAlignment = Alignment.TopCenter
        ) {

            when (uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is HomeUiState.Success -> {

                    Column() {
                        val itemSize = 56.dp
                        val itemSpacing = 56.dp
                        val minScale = 0.6f
                        val maxScale = 1.5f

                        val listState = rememberLazyListState()
                        val flingBehavior = rememberSnapFlingBehavior(
                            lazyListState = listState,
                            snapPosition = SnapPosition.Center,
                        )

                        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                            val edgePadding = maxOf(0.dp, (maxWidth - itemSize) / 2)

                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                state = listState,
                                flingBehavior = flingBehavior,
                                contentPadding = PaddingValues(horizontal = edgePadding),
                                horizontalArrangement = Arrangement.spacedBy(itemSpacing),
                            ) {
                                itemsIndexed(uiState.data.content) { index, item ->
                                    AsyncImage(
                                        model = item.logoUrl,
                                        contentDescription = item.name,
                                        modifier = Modifier
                                            .size(itemSize)
                                            .graphicsLayer {
                                                val scale = listState.centerScale(
                                                    index = index,
                                                    step = (itemSize + itemSpacing).toPx(),
                                                    minScale = minScale,
                                                    maxScale = maxScale,
                                                )
                                                scaleX = scale
                                                scaleY = scale
                                            },
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(18.dp))


//                        Experimental Stuff
//                        val carouselState = rememberCarouselState() { 5 }
//
//                        HorizontalCenteredHeroCarousel(
//                            modifier = Modifier.fillMaxWidth(),
//                            state = carouselState,
//                            itemSpacing = 12.dp,
//                            flingBehavior = CarouselDefaults.multiBrowseFlingBehavior(state = carouselState ),
//                        ) {
//                            Card(
//                                modifier = Modifier.fillMaxWidth().height(350.dp)
//                            ) {
//
//
//                            }
//                        }
                    }

                }

                is HomeUiState.Error -> {
                    Text(uiState.message)
                }
            }

        }


    }
}

private fun LazyListState.centerScale(
    index: Int,
    step: Float,
    minScale: Float,
    maxScale: Float,
): Float {
    val info = layoutInfo
    val item = info.visibleItemsInfo.firstOrNull { it.index == index } ?: return minScale
    val viewportCenter = (info.viewportStartOffset + info.viewportEndOffset) / 2
    val itemCenter = item.offset + item.size / 2
    val distance = abs(itemCenter - viewportCenter).toFloat()
    val proximity = (1f - distance / step).coerceIn(0f, 1f)
    return minScale + (maxScale - minScale) * proximity
}