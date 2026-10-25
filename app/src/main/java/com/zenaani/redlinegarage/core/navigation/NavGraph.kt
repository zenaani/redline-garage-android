package com.zenaani.redlinegarage.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zenaani.redlinegarage.ui.bike.BikeScreen
import com.zenaani.redlinegarage.ui.home.HomeScreen
import com.zenaani.redlinegarage.ui.models.ModelsScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.Home) {
        composable<Route.Home> {
            HomeScreen(
                onItemClick = { navController.navigate(Route.Bike(modelId = it)) },
                onExploreBannerClick = { navController.navigate(Route.Bike(1)) })
        }

        composable<Route.Model> {
            ModelsScreen(
                onItemClick = { modelId ->
                    navController.navigate(Route.Bike(modelId))
                }
            )
        }

        composable<Route.Bike> {
            BikeScreen()
        }
    }
}