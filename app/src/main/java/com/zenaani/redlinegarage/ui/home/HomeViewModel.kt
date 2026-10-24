package com.zenaani.redlinegarage.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenaani.redlinegarage.data.dto.BrandResponseDto
import com.zenaani.redlinegarage.data.dto.ModelResponseDto
import com.zenaani.redlinegarage.data.repository.BrandRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BrandRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var bikesJob: Job? = null

    init {
        getBrands()
    }

    private fun getBrands() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(brandsState = ContentState.Loading)
            }

            try {
                val response = repository.getBrands().getOrThrow()

                _uiState.update {
                    it.copy(
                        brands = response,
                        brandsState = ContentState.Success
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        brandsState = ContentState.Error(
                            e.message ?: "Unable to load brands"
                        )
                    )
                }
            }

            onBrandSelection(1)
        }
    }

    fun onBrandSelection(brandId: Int) {
        bikesJob?.cancel()
        _uiState.update {
            it.copy(
                selectedBrandId = brandId,
                bikes = null,
                bikesState = ContentState.Loading
            )
        }

        bikesJob = viewModelScope.launch {
            try {
                val response = repository.getModels(brandId).getOrThrow()

                _uiState.update {
                    it.copy(
                        bikes = response,
                        bikesState = ContentState.Success
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        brandsState = ContentState.Error(
                            e.message ?: "Unable to load bikes"
                        )
                    )
                }
            }
        }
    }

    fun retryBrands() {
        getBrands()
    }

}

