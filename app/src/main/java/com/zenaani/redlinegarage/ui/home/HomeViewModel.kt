package com.zenaani.redlinegarage.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenaani.redlinegarage.data.repository.BrandRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BrandRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getBrands()
    }

    private fun getBrands() {
        viewModelScope.launch {

            repository.getBrands()
                .onSuccess { response ->
                    _uiState.update {
                        HomeUiState.Success(response)
                    }
                }
                .onFailure {  exception ->
                    _uiState.update {
                        HomeUiState.Error(
                            exception.message ?: "Unknown Error"
                        )
                    }
                }
        }
    }

}

