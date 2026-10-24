package com.zenaani.redlinegarage.ui.bike

import android.R.attr.data
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
class BikeViewModel @Inject constructor(
    private val repository: BrandRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val modelId = savedStateHandle.toRoute<Route.Bike>().modelId

    private val _uiState = MutableStateFlow<BikeUiState>(BikeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getBikeDetails(1)
    }

    private fun getBikeDetails(modelId: Int) {
        viewModelScope.launch {
            repository.getBikeDetails(modelId)
                .onSuccess { bikeDetailsDto ->
                    _uiState.update {
                        BikeUiState.Success(data = bikeDetailsDto)
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        BikeUiState.Error(message = exception.message ?: "Unknown Error")
                    }
                }

        }

    }




}