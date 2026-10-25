package com.zenaani.redlinegarage.ui.models

import com.zenaani.redlinegarage.data.dto.ModelPagedDto

sealed interface ModelsUiState {
    data object Loading : ModelsUiState
    data class Success(val data: ModelPagedDto) : ModelsUiState
    data class Error(val message: String) : ModelsUiState
}