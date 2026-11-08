package com.zenaani.redlinegarage.ui.accessory

import com.zenaani.redlinegarage.data.dto.AccessoryDto
import com.zenaani.redlinegarage.data.dto.UserReviewDto
import com.zenaani.redlinegarage.data.dto.UserReviewPagedDto
import com.zenaani.redlinegarage.ui.home.ContentState

data class AccessoryUiState (
    val accessoryDetails: AccessoryDto? = null,
    val accessoryState: ContentState = ContentState.Loading,
    val userReviews: UserReviewPagedDto? = null,
    val userReviewsState: ContentState = ContentState.Success
)