package com.example.modul_3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.modul_3.screen.DetailScreen
import com.example.modul_3.screen.ListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(navController)
        }
        composable(
            "detail/{itemTitle}",
            arguments = listOf(navArgument("itemTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemTitle = backStackEntry.arguments?.getString("itemTitle") ?: "No Title"
            DetailScreen(itemTitle)
        }
    }
}