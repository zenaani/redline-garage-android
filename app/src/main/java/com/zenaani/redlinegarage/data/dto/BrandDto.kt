package com.zenaani.redlinegarage.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BrandPagedDto(
    val content: List<BrandDto> = emptyList(),
    val last: Boolean,
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class BrandDto(
    val country: String,
    val id: Int,
    val logoUrl: String,
    val name: String
)