package com.zenaani.redlinegarage.ui.home

import com.zenaani.redlinegarage.data.dto.BikeDetailsDto
import com.zenaani.redlinegarage.data.dto.BrandResponseDto
import com.zenaani.redlinegarage.data.dto.ModelResponseDto


data class HomeUiState(
    val brands: BrandResponseDto? = null,
    val bikes: ModelResponseDto? = null,

    val selectedBrandId: Int? = null,
    val brandsState: ContentState = ContentState.Loading,
    val bikesState: ContentState = ContentState.Idle
)
sealed interface ContentState {
    data object Idle : ContentState
    data object Loading : ContentState
    data object Success : ContentState
    data class Error(val message: String) : ContentState
}