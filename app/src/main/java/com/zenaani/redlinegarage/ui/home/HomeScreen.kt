package com.zenaani.redlinegarage.ui.home

import android.R
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TargetedFlingBehavior
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.core.components.BikeDetailTab
import com.zenaani.redlinegarage.core.components.DimensionsIcon
import com.zenaani.redlinegarage.core.components.EngineItemData
import com.zenaani.redlinegarage.core.components.EngineSpecsGrid
import com.zenaani.redlinegarage.core.components.ExploreCard
import com.zenaani.redlinegarage.core.components.FeaturedCard
import com.zenaani.redlinegarage.core.components.SpecCard
import com.zenaani.redlinegarage.core.components.SpecItem
import com.zenaani.redlinegarage.core.components.performance.AccelerationCard
import com.zenaani.redlinegarage.core.components.performance.AccelerationRun
import com.zenaani.redlinegarage.core.components.performance.ChartPoint
import com.zenaani.redlinegarage.core.components.performance.ChartSeries
import com.zenaani.redlinegarage.core.components.performance.PerfColors
import com.zenaani.redlinegarage.core.components.performance.PowerTorqueCard
import com.zenaani.redlinegarage.core.components.performance.pts
import com.zenaani.redlinegarage.ui.bike.KeyFigureItem
import com.zenaani.redlinegarage.ui.theme.BodoniModa
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onItemClick: (Int) -> Unit) {
    Scaffold(
        topBar = { },
    ) { innerPadding ->

        val uiState = viewModel.uiState.collectAsState().value

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            when (uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is HomeUiState.Success -> {

                    val data = uiState.data

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {

                        // Top Banner
                        Box() {
                            Image(
                                painter = painterResource(com.zenaani.redlinegarage.R.drawable.honda_cb300r),
                                contentDescription = null,
                                modifier = Modifier.offset(x = 40.dp)
                            )

                            // Gradient Overlay (left -> right)
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Black, Color.Transparent
                                            )
                                        )
                                    )
                            )

                            // Gradient Overlay (bottom -> top)
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent, Color.Black
                                            )
                                        )
                                    )
                            )

                            // Bike Intro
                            Column(
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp)
                            ) {
                                Text(
                                    "REDLINE",
                                    fontSize = 24.sp,
                                    lineHeight = 2.sp,
                                    color = Color.White,
                                    fontFamily = BodoniModa,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "GARAGE",
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 3.sp
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "REAL BIKES. REAL NUMBERS",
                                    fontSize = 8.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(60.dp))
                                Text(
                                    "HONDA",
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 3.sp
                                )
                                Text(
                                    "CB300R",
                                    fontSize = 34.sp,
                                    lineHeight = 2.sp,
                                    color = Color.White,
                                    fontFamily = BodoniModa,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "MINIMAL. POWERFUL. BUILT \nFOR THE EVERYDAY RIDE.",
                                    fontSize = 8.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 1.sp
                                )

                                Spacer(modifier = Modifier.height(18.dp))

                                Text(
                                    buildAnnotatedString {
                                        withStyle(SpanStyle(color = Color.Red)) {
                                            append("EXPLORE DETAILS ")
                                        }
                                        withStyle(SpanStyle(color = Color.Gray)) {
                                            append("→")
                                        }
                                    },
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 1.sp
                                )
                            }
                        }


                        // Explore Motorcycles
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "EXPLORE MOTORCYCLES",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontFamily = BodoniModa,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "View all →",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 1.sp
                                )
                            }

                            Spacer(Modifier.height(12.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(5) {
                                    ExploreCard()
                                }
                            }

                            Spacer(Modifier.height(12.dp))
                            FeaturedCard()
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }

                is HomeUiState.Error -> {
                    Text(uiState.message)
                }
            }

        }


    }
}
