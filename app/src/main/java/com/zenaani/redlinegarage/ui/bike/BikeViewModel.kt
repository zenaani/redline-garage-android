package com.zenaani.redlinegarage.ui.bike

import android.R.attr.data
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.zenaani.redlinegarage.core.navigation.Route
import com.zenaani.redlinegarage.data.repository.BrandRepository
import com.zenaani.redlinegarage.ui.home.ContentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
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

    val modelId = savedStateHandle.toRoute<Route.Bike>().modelId

    private val _uiState = MutableStateFlow<BikeUiState>(BikeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getBikeDetails(modelId)
    }

    private fun getBikeDetails(modelId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(bikeDetailsState = ContentState.Loading)
            }

            try {
                val response = repository.getBikeDetails(modelId).getOrThrow()
                _uiState.update {
                    it.copy(bikeDetails = response, bikeDetailsState = ContentState.Success)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        bikeDetailsState = ContentState.Error(
                            e.message ?: "Unable to load bike details"
                        )
                    )
                }
            }
        }
    }

    fun getAccessories(bikeId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(accessoriesState = ContentState.Loading)
            }

            try {
                val response = repository.getAccessories(bikeId).getOrThrow()
                _uiState.update {
                    it.copy(
                        accessories = response,
                        accessoriesState = ContentState.Success
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        accessoriesState = ContentState.Error(
                            e.message ?: "Unable to load accessories"
                        )
                    )
                }
            }

        }
    }




}