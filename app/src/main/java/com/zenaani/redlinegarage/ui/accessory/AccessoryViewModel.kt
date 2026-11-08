package com.zenaani.redlinegarage.ui.accessory

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
class AccessoryViewModel @Inject constructor(
    private val repository: BrandRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val accessoryId = savedStateHandle.toRoute<Route.Accessory>().accessoryId

    private val _uiState = MutableStateFlow(AccessoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAccessoryDetails()
        getUserReviews()
    }

    private fun getUserReviews() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(userReviewsState = ContentState.Loading)
            }

            try {
                val response = repository.getUserReviews(accessoryId).getOrThrow()
                _uiState.update {
                    it.copy(userReviews = response, userReviewsState = ContentState.Success)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        userReviewsState = ContentState.Error(
                            e.message ?: "Unable to load User Reviews"
                        )
                    )
                }
            }

        }
    }

    private fun getAccessoryDetails() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(accessoryState = ContentState.Loading)
            }

            try {
                val response = repository.getAccessoryDetails(accessoryId).getOrThrow()
                _uiState.update {
                    it.copy(accessoryDetails = response, accessoryState = ContentState.Success)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        userReviewsState = ContentState.Error(
                            e.message ?: "Unable to load Accessory Details"
                        )
                    )
                }
            }
        }
    }




}