package com.zenaani.redlinegarage.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessoryPagedDto(
    val content: List<AccessoryDto> = emptyList(),
    val last: Boolean,
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class AccessoryDto(
    val id: Long,
    val name: String? = null,
    val description: String? = null,
    val price: String? = null,
    val imageUrl: String? = null
)