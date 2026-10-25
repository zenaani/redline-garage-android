package com.zenaani.redlinegarage.ui.home

import android.R
import android.R.attr.screenSize
import android.graphics.Paint
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.core.components.BikeCard
import com.zenaani.redlinegarage.core.components.BikeDetailTab
import com.zenaani.redlinegarage.core.components.BrandPill
import com.zenaani.redlinegarage.core.components.DimensionsIcon
import com.zenaani.redlinegarage.core.components.EngineItemData
import com.zenaani.redlinegarage.core.components.EngineSpecsGrid
import com.zenaani.redlinegarage.core.components.ExploreCard
import com.zenaani.redlinegarage.core.components.ExploreSection
import com.zenaani.redlinegarage.core.components.FeaturedCard
import com.zenaani.redlinegarage.core.components.IconBadge
import com.zenaani.redlinegarage.core.components.NavBar
import com.zenaani.redlinegarage.core.components.SearchReveal
import com.zenaani.redlinegarage.core.components.SpecCard
import com.zenaani.redlinegarage.core.components.SpecItem
import com.zenaani.redlinegarage.core.components.TopBannerHome
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
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
    onExploreBannerClick: () -> Unit
) {
    Scaffold(
        topBar = { },
    ) { innerPadding ->

        val uiState = viewModel.uiState.collectAsState().value

        var isSearch by remember { mutableStateOf(false) }
        var brandName by remember { mutableStateOf("Honda") }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                TopBannerHome(onClick = onExploreBannerClick)

                // Explore Motorcycles
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    ExploreSection()
                    Spacer(Modifier.height(12.dp))
                    FeaturedCard()
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "EXPLORE BY BRAND",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = BodoniModa,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    when (uiState.brandsState) {
                        is ContentState.Error -> {
                            Text(uiState.brandsState.message)
                        }

                        ContentState.Idle -> {}
                        ContentState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        ContentState.Success -> {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(uiState.brands?.content ?: emptyList()) {
                                    BrandPill(name = it.name, logo = it.logoUrl, onClick = {
                                        viewModel.onBrandSelection(it.id)
                                        brandName = it.name
                                    })
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    when (uiState.bikesState) {
                        is ContentState.Error -> {
                            Text(uiState.bikesState.message)
                        }

                        ContentState.Idle -> {}
                        ContentState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        ContentState.Success -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
//                                items(uiState.bikes?.content ?: emptyList()) {
//                                    BikeCard(brand = brandName, model = it.modelName)
//                                }
                                uiState.bikes?.content?.forEach {
                                    BikeCard(
                                        brand = brandName,
                                        model = it.modelName,
                                        segment = it.segment,
                                        variant = it.variant,
                                        onClick = { onItemClick(it.id) })
                                }
                            }

                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

            }

            SearchReveal(isSearchOpen = isSearch, onSearchClose = { isSearch = false })



            // Bottom Dark Gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
            )

            // Bottom Navigation Bar
            var selectedTab by remember { mutableIntStateOf(0) }
            NavBar(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                selectedIndex = selectedTab,
                onItemSelected = { selectedTab = it }
            )
        }


    }
}
