package com.zenaani.redlinegarage.ui.bike

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenaani.redlinegarage.R
import com.zenaani.redlinegarage.core.components.AccessoryCard
import com.zenaani.redlinegarage.core.components.performance.AccelerationCard
import com.zenaani.redlinegarage.core.components.performance.AccelerationRun
import com.zenaani.redlinegarage.core.components.BikeDetailTab
import com.zenaani.redlinegarage.core.components.DimensionsIcon
import com.zenaani.redlinegarage.core.components.EngineItemData
import com.zenaani.redlinegarage.core.components.EngineSpecsGrid
import com.zenaani.redlinegarage.core.components.SpecCard
import com.zenaani.redlinegarage.core.components.SpecItem
import com.zenaani.redlinegarage.core.components.performance.ChartPoint
import com.zenaani.redlinegarage.core.components.performance.ChartSeries
import com.zenaani.redlinegarage.core.components.performance.PerfColors
import com.zenaani.redlinegarage.core.components.performance.PowerTorqueCard
import com.zenaani.redlinegarage.core.components.performance.pts
import com.zenaani.redlinegarage.ui.home.ContentState
import com.zenaani.redlinegarage.ui.theme.BodoniModa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeScreen(viewModel: BikeViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
        },
    ) { innerPadding ->

        val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {

            when (uiState.bikeDetailsState) {
                is ContentState.Idle -> {

                }

                is ContentState.Loading -> {
                    CircularProgressIndicator()
                }

                is ContentState.Success -> {
                    val data = uiState.bikeDetails
                    val engineData = data?.engine
                    val performanceData = data?.performance

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {

                        Box() {
                            Image(
                                painter = painterResource(R.drawable.honda_cb300r),
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
                                                Color.Black,
                                                Color.Transparent
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
                                                Color.Transparent,
                                                Color.Black
                                            )
                                        )
                                    )
                            )

                            // Bike Intro
                            Column(
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp)
                            ) {
                                Text(
                                    "The Ride",
                                    fontSize = 8.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 3.sp
                                )
                                Text(
                                    data?.brandName ?: "-",
                                    fontSize = 34.sp,
                                    color = Color.White,
                                    fontFamily = BodoniModa,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    data?.modelName ?: "-",
                                    fontSize = 34.sp,
                                    lineHeight = 2.sp,
                                    color = Color.White,
                                    fontFamily = BodoniModa,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Neo Sports Cafe",
                                    fontSize = 8.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 3.sp
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    "Minimal Lines",
                                    fontSize = 8.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(
                                    "Maximum Thrills.",
                                    fontSize = 8.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 1.sp
                                )
                            }
                        }

                        // Switchable Tab
                        var selectedTab by remember { mutableStateOf(BikeDetailTab.OVERVIEW) }
                        BikeDetailTab(
                            selectedTab = selectedTab,
                            onTabSelected = { selectedTab = it })


                        // Key Figures
                        Column(
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                        ) {
                            Spacer(modifier = Modifier.height(18.dp))
                            Text(
                                "KEY FIGURES",
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraLight,
                                letterSpacing = 3.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                KeyFigureItem(
                                    "Max Power",
                                    engineData?.maxPowerBhp?.toString() ?: "-",
                                    "hp"
                                )
                                VerticalDivider(modifier = Modifier.height(60.dp))
                                KeyFigureItem(
                                    "Max Torque",
                                    engineData?.maxTorqueNm?.toString() ?: "-",
                                    "Nm",
                                )
                                VerticalDivider(modifier = Modifier.height(60.dp))
                                KeyFigureItem(
                                    "0-60 km/h",
                                    performanceData?.zeroToSixtyAvg?.toString() ?: "-",
                                    "s",
                                )
                                VerticalDivider(modifier = Modifier.height(60.dp))
                                KeyFigureItem(
                                    "Top Speed",
                                    performanceData?.topSpeedTestedKmh?.toString() ?: "-",
                                    "Km/h",
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        when (selectedTab) {
                            BikeDetailTab.OVERVIEW -> {

                            }

                            BikeDetailTab.PERFORMANCE -> {
                                AccelerationCard(
                                    runs = listOf(
                                        AccelerationRun(
                                            "0 – 60 km/h",
                                            performanceData?.zeroToSixtyBest?.toFloat() ?: 0f
                                        ),
                                        AccelerationRun(
                                            "0 – 100 km/h",
                                            performanceData?.zeroToHundredBest?.toFloat() ?: 0f
                                        ),
                                    ),
                                )

                                val power = ChartSeries(
                                    label = "Power (hp)",
                                    color = PerfColors.Power,
                                    points = pts(
                                        1800f to 0.5f,
                                        2000f to 0.8f,
                                        3000f to 4.3f,
                                        4000f to 9.3f,
                                        5000f to 14.5f,
                                        6000f to 19.8f,
                                        7000f to 25.0f,
                                        8000f to 28.8f,
                                        9000f to 31.1f,
                                        9600f to 30.4f,
                                        10200f to 28.5f,
                                    ),
                                )
                                val torque = ChartSeries(
                                    label = "Torque (Nm)",
                                    color = PerfColors.Torque,
                                    points = pts(
                                        1800f to 2.5f,
                                        2500f to 5.8f,
                                        3000f to 9.0f,
                                        4000f to 16.2f,
                                        5000f to 20.8f,
                                        6000f to 22.5f,
                                        6500f to 22.8f,
                                        7000f to 22.6f,
                                        8000f to 21.2f,
                                        9000f to 18.8f,
                                        10000f to 16.0f,
                                        10200f to 15.2f,
                                    ),
                                )

                                PowerTorqueCard(
                                    power = power,
                                    torque = torque,
                                    peakPower = ChartPoint(
                                        9000f,
                                        engineData?.maxPowerBhp?.toFloat() ?: 0f
                                    ),
                                    peakTorque = ChartPoint(
                                        7750f,
                                        engineData?.maxTorqueNm?.toFloat() ?: 0f
                                    ),
                                )
                            }

                            BikeDetailTab.SPECS -> {
                                //Engine Info
                                Column(
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(
                                        "Engine Details",
                                        fontSize = 22.sp,
                                        color = Color.White,
                                        fontFamily = BodoniModa,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))

                                    val engineItems = buildList {
                                        engineData?.let { engine ->
                                            add(
                                                EngineItemData(
                                                    "Engine Type",
                                                    engine.engineType.orEmpty()
                                                )
                                            )
                                            add(
                                                EngineItemData(
                                                    "Engine Capacity",
                                                    engine.engineCc?.let { "$it cc" } ?: "N/A"))
                                            add(
                                                EngineItemData(
                                                    "Max Power",
                                                    engine.maxPowerBhp?.let { "$it bhp" } ?: "N/A"))
                                            add(
                                                EngineItemData(
                                                    "Power RPM",
                                                    engine.powerRpm?.let { "$it rpm" } ?: "N/A"))
                                            add(
                                                EngineItemData(
                                                    "Max Torque",
                                                    engine.maxTorqueNm?.let { "$it Nm" } ?: "N/A"))
                                            add(
                                                EngineItemData(
                                                    "Torque RPM",
                                                    engine.torqueRpm?.let { "$it rpm" } ?: "N/A"))
                                            add(
                                                EngineItemData(
                                                    "Transmission",
                                                    engine.transmission.orEmpty()
                                                )
                                            )
                                            add(
                                                EngineItemData(
                                                    "Kerb Weight",
                                                    engine.kerbWeightKg?.let { "$it kg" } ?: "N/A"))
                                        }
                                    }
                                    EngineSpecsGrid(engineItems)

                                    Spacer(modifier = Modifier.height(12.dp))

                                    SpecCard(
                                        title = "Dimensions & Weight",
                                        icon = DimensionsIcon,
                                        items = listOf(
                                            SpecItem("Overall Length", "2,080 mm"),
                                            SpecItem("Overall Width", "805 mm"),
                                            SpecItem("Overall Height", "1,055 mm"),
                                            SpecItem("Wheelbase", "1,406 mm"),
                                            SpecItem("Seat Height", "799 mm"),
                                            SpecItem("Kerb Weight", "146 kg"),
                                        ),
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    SpecCard(
                                        title = "Chassis & Suspension",
                                        icon = DimensionsIcon,
                                        items = listOf(
                                            SpecItem("Front Suspension", "Showa 41 mm USD Forks"),
                                            SpecItem("Rear Suspension", "Monoshock (Pro-Link)"),
                                            SpecItem("Front Brake", "310 mm Disc (ABS)"),
                                            SpecItem("Rear Brake", "240 mm Disc (ABS)"),
                                        ),
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    SpecCard(
                                        title = "Tyres & Wheels",
                                        icon = DimensionsIcon,
                                        items = listOf(
                                            SpecItem("Front Tyre", "110/70 R17"),
                                            SpecItem("Rear Tyre", "150/60 R17"),
                                            SpecItem("Front Wheel", "17 inch"),
                                            SpecItem("Rear Wheel", "17 inch"),
                                        ),
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    SpecCard(
                                        title = "Fuel & Capacity",
                                        icon = DimensionsIcon,
                                        items = listOf(
                                            SpecItem("Fuel Tank Capacity", "9.7 L"),
                                            SpecItem("Reserve Capacity", "–"),
                                            SpecItem("Mileage (Approx.)", "28–30 km/l"),
                                        ),
                                    )
                                }
                            }

                            BikeDetailTab.ACCESSORIES -> {
                                LaunchedEffect(viewModel.modelId) {
                                    viewModel.getAccessories(viewModel.modelId)
                                }
                                when (uiState.accessoriesState) {
                                    is ContentState.Error -> {
                                        Text(uiState.accessoriesState.message)
                                    }

                                    ContentState.Idle -> {}
                                    ContentState.Loading -> {
                                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                                    }

                                    ContentState.Success -> {
                                        Column(
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {

                                                Text(
                                                    "Popular Accessories",
                                                    fontSize = 22.sp,
                                                    color = Color.White,
                                                    fontFamily = BodoniModa,
                                                    fontWeight = FontWeight.SemiBold,
                                                )

                                                Text(
                                                    text = "View all →",
                                                    color = Color.White,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.ExtraLight,
                                                    letterSpacing = 1.sp
                                                )

                                            }


                                            Spacer(modifier = Modifier.height(4.dp))

                                            Text(
                                                "Top picks for a better ride",
                                                fontSize = 8.sp,
                                                color = Color.Gray,
                                                fontWeight = FontWeight.ExtraLight,
                                                letterSpacing = 1.sp
                                            )

                                            Spacer(modifier = Modifier.height(12.dp))

                                            LazyVerticalGrid(
                                                columns = GridCells.Fixed(2),
                                                modifier = Modifier.height(800.dp),
                                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                                verticalArrangement = Arrangement.spacedBy(12.dp)
                                            ) {
                                                items(uiState.accessories?.content ?: emptyList()) {
                                                    AccessoryCard(name = it.name ?: "-", price = it.price ?: "")
                                                }
                                            }
                                        }

                                    }
                                }


                            }
                        }


                        Spacer(modifier = Modifier.height(12.dp))
                    }

                }

                is ContentState.Error -> {
                    Text(uiState.bikeDetailsState.message)
                }
            }

        }


    }
}

@Composable
fun KeyFigureItem(title: String, value: String, unit: String) {
    Column() {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                value,
                fontSize = 22.sp,
                color = Color.White,
                fontFamily = BodoniModa,
                fontWeight = FontWeight.Bold
            )
            Text(
                unit,
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = BodoniModa,
                fontWeight = FontWeight.Bold
            )
        }
        Text(title, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.ExtraLight)
    }
}
