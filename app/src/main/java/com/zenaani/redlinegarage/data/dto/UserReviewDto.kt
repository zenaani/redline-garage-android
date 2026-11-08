package com.zenaani.redlinegarage.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserReviewPagedDto(
    val content: List<UserReviewDto> = emptyList(),
    val last: Boolean,
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class UserReviewDto(
    val id: Long,
    val name: String,
    val stars: Double,
    val message: String
)