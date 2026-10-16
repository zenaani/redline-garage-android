package com.zenaani.redlinegarage.ui.models

import com.zenaani.redlinegarage.data.dto.ModelResponseDto

sealed interface ModelsUiState {
    data object Loading : ModelsUiState
    data class Success(val data: ModelResponseDto) : ModelsUiState
    data class Error(val message: String) : ModelsUiState
}