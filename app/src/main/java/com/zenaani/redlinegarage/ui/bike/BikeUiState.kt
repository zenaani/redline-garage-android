package com.zenaani.redlinegarage.ui.bike

import com.zenaani.redlinegarage.data.dto.BikeDetailsDto

sealed interface BikeUiState {
    data object Loading : BikeUiState
    data class Success(val data: BikeDetailsDto) : BikeUiState
    data class Error(val message: String) : BikeUiState
}