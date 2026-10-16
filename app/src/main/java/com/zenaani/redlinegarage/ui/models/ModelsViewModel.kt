package com.zenaani.redlinegarage.ui.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.zenaani.redlinegarage.core.navigation.Route
import com.zenaani.redlinegarage.data.repository.BrandRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelsViewModel @Inject constructor(
    private val repository: BrandRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val brandId = savedStateHandle.toRoute<Route.Model>().brandId

    private val _uiState = MutableStateFlow<ModelsUiState>(ModelsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getModels(brandId = brandId)
    }

    private fun getModels(brandId: Int) {
        viewModelScope.launch {
            repository.getModels(brandId)
                .onSuccess { data ->
                    _uiState.update {
                        ModelsUiState.Success(data)
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        ModelsUiState.Error(exception.message ?: "Unknown Error")
                    }
                }
        }

    }


}