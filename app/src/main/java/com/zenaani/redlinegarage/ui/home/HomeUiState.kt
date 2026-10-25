package com.zenaani.redlinegarage.ui.home

import com.zenaani.redlinegarage.data.dto.BrandPagedDto
import com.zenaani.redlinegarage.data.dto.ModelPagedDto


data class HomeUiState(
    val brands: BrandPagedDto? = null,
    val bikes: ModelPagedDto? = null,
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