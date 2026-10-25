package com.zenaani.redlinegarage.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ModelPagedDto(
    val content: List<ModelDto>,
    val last: Boolean,
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class ModelDto(
    val id: Int,
    val modelName: String,
    val variant: String,
    val segment: String
)