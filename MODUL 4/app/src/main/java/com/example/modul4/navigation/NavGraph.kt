package com.example.modul4.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.example.modul4.screen.DetailScreen
import com.example.modul4.screen.ListScreen
import com.example.modul4.viewModel.ItemViewModel
import com.example.modul4.viewModel.ItemViewModelFactory

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val factory = ItemViewModelFactory()
    val itemViewModel: ItemViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "list", modifier = modifier) {
        composable("list") {
            ListScreen(navController = navController, viewModel = itemViewModel)
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailScreen(id = id, viewModel = itemViewModel)
        }
    }
}
