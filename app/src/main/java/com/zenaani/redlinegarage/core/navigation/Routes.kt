package com.zenaani.redlinegarage.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    object Home : Route
    @Serializable
    data class Model(val brandId: Int) : Route

    @Serializable
    data class Bike(val modelId: Int) : Route
}