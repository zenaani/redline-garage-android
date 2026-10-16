package com.zenaani.redlinegarage.ui.home

import com.zenaani.redlinegarage.data.dto.BrandResponseDto

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val data : BrandResponseDto
    ) : HomeUiState
    data class Error(
        val message: String
    ) : HomeUiState
}