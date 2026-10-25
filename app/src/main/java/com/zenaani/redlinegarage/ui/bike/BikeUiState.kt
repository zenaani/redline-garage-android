package com.zenaani.redlinegarage.ui.bike

import com.zenaani.redlinegarage.data.dto.AccessoryDto
import com.zenaani.redlinegarage.data.dto.AccessoryPagedDto
import com.zenaani.redlinegarage.data.dto.BikeDetailsDto
import com.zenaani.redlinegarage.ui.home.ContentState


data class BikeUiState(
    val bikeDetails: BikeDetailsDto? = null,
    val accessories: AccessoryPagedDto? = null,
    val bikeDetailsState: ContentState = ContentState.Loading,
    val accessoriesState: ContentState = ContentState.Idle
)