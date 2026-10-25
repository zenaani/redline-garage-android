//package com.zenaani.redlinegarage.ui.profile
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.VerticalDivider
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
//import com.zenaani.redlinegarage.R
//import com.zenaani.redlinegarage.core.components.BikeDetailTab
//import com.zenaani.redlinegarage.ui.bike.BikeUiState
//import com.zenaani.redlinegarage.ui.bike.BikeViewModel
//import com.zenaani.redlinegarage.ui.theme.BodoniModa
//
//@Composable
//fun ProfileScreen(viewModel: BikeViewModel = hiltViewModel()) {
//    Scaffold(
//    ) { innerPadding ->
//
//        val uiState = viewModel.uiState.collectAsState().value
//
//        Box(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize(), contentAlignment = Alignment.Center
//        ) {
//
//            when (uiState) {
//                is BikeUiState.Loading -> {
//                    CircularProgressIndicator()
//                }
//
//                is BikeUiState.Success -> {
//                    val data = uiState.data
//                    val engineData = data.engine
//                    val performanceData = data.performance
//
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.spacedBy(12.dp),
//                    ) {
//
//                        Box() {
//                            Image(
//                                painter = painterResource(R.drawable.honda_cb300r),
//                                contentDescription = null,
//                                modifier = Modifier.offset(x = 40.dp)
//                            )
//
//                            // Gradient Overlay (left -> right)
//                            Box(
//                                modifier = Modifier
//                                    .matchParentSize()
//                                    .background(
//                                        Brush.horizontalGradient(
//                                            colors = listOf(
//                                                Color.Black,
//                                                Color.Transparent
//                                            )
//                                        )
//                                    )
//                            )
//
//                            // Gradient Overlay (bottom -> top)
//                            Box(
//                                modifier = Modifier
//                                    .align(Alignment.BottomCenter)
//                                    .fillMaxWidth()
//                                    .height(50.dp)
//                                    .background(
//                                        Brush.verticalGradient(
//                                            colors = listOf(
//                                                Color.Transparent,
//                                                Color.Black
//                                            )
//                                        )
//                                    )
//                            )
//
//                            // Profile Info
//                            Column(
//                                modifier = Modifier.padding(start = 8.dp, top = 36.dp)
//                            ) {
//                                Box(
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Box(modifier = Modifier.size(62.dp).background(color = Color.White, shape = CircleShape), )
//                                    Image(
//                                        painterResource(R.drawable.honda_cb300r),
//                                        contentDescription = null,
//                                        contentScale = ContentScale.Crop,
//                                        modifier = Modifier
//                                            .size(60.dp)
//                                            .clip(
//                                                CircleShape
//                                            )
//                                    )
//                                }
//
//                                Spacer(modifier = Modifier.height(4.dp))
//                                Text(
//                                    "Zenaani Suresh",
//                                    fontSize = 24.sp,
//                                    color = Color.White,
//                                    fontFamily = BodoniModa,
//                                    fontWeight = FontWeight.SemiBold
//                                )
//                                Spacer(modifier = Modifier.size(8.dp))
//                                Text(
//                                    "Better Roads",
//                                    fontSize = 8.sp,
//                                    color = Color.White,
//                                    fontWeight = FontWeight.ExtraLight,
//                                    letterSpacing = 1.sp
//                                )
//                                Spacer(modifier = Modifier.size(4.dp))
//                                Text(
//                                    "Bigger Dreams.",
//                                    fontSize = 8.sp,
//                                    color = Color.White,
//                                    fontWeight = FontWeight.ExtraLight,
//                                    letterSpacing = 1.sp
//                                )
//                            }
//                        }
//
//                    }
//
//                }
//
//                is BikeUiState.Error -> {
//                    Text(uiState.message)
//                }
//            }
//
//        }
//
//
//    }
//}
