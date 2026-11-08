package com.zenaani.redlinegarage.ui.accessory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zenaani.redlinegarage.R
import com.zenaani.redlinegarage.core.components.UserReviewSection
import com.zenaani.redlinegarage.ui.home.ContentState
import com.zenaani.redlinegarage.ui.theme.BodoniModa

@Composable
fun AccessoryScreen(viewModel: AccessoryViewModel = hiltViewModel()) {
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

            when (uiState.userReviewsState) {
                is ContentState.Idle -> {

                }

                is ContentState.Loading -> {
                    CircularProgressIndicator()
                }

                is ContentState.Success -> {
                    val accessoryData = uiState.accessoryDetails

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
                                    accessoryData?.name ?: "-",
                                    fontSize = 34.sp,
                                    color = Color.White,
                                    fontFamily = BodoniModa,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    accessoryData?.description ?: "-",
                                    fontSize = 8.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraLight,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.size(64.dp))
                                Text(
                                    text = accessoryData?.price?.let { "Rs.$it" } ?: "-",
                                    fontSize = 24.sp,
                                    fontFamily = BodoniModa,
                                    color = Color.White,
                                    fontWeight = FontWeight.Light,
                                    letterSpacing = 1.sp,

                                )
                            }


                        }

                        // User Reviews
                        uiState.userReviews?.let { reviewData ->
                            UserReviewSection(pagedDto = reviewData)
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                is ContentState.Error -> { }
            }
        }
    }
}

@Preview
@Composable
fun AccessoryScreenPreview() {
    AccessoryScreen()
}