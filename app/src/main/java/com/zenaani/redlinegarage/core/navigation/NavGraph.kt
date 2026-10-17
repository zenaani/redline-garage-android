package com.zenaani.redlinegarage.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zenaani.redlinegarage.ui.bike.BikeScreen
import com.zenaani.redlinegarage.ui.home.HomeScreen
import com.zenaani.redlinegarage.ui.models.ModelsScreen
import com.zenaani.redlinegarage.ui.profile.ProfileScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.Home) {
        composable<Route.Home> {
            HomeScreen(onItemClick = { })
//            HomeScreen(
//                onItemClick = { brandId ->
//                    navController.navigate(Route.Model(brandId))
//                }
//            )
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