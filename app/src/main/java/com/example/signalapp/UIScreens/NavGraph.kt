package com.example.signalapp.UIScreens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.signalapp.room.ViewModels
import kotlinx.coroutines.CoroutineScope


sealed class Screens(val route: String){
    object MainScreen: Screens("start")
    object DeteilScreen: Screens("favorite")
    object Favorite: Screens("deteil")
}

@Composable
fun NavGraphs(
    navHostController: NavHostController,
    coroutineScope: CoroutineScope,
    viewModels: ViewModels
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.MainScreen.route
    ) {
        composable(Screens.MainScreen.route) {
            Greeting(
                navHostController = navHostController,
                coroutineScope = coroutineScope,
                viewModels = viewModels
            )
        }
        composable(Screens.DeteilScreen.route + "{name}/{desk}/{imageUri}") { backStackEntry ->
            DeteilScreen(
                name = backStackEntry.arguments?.getString("name").toString(),
                description = backStackEntry.arguments?.getString("desk").toString(),
                imageUri = backStackEntry.arguments?.getString("imageUri").toString()
            )
        }
        composable(Screens.Favorite.route) {
            FavoritesScreen(
                viewModels = viewModels,
                navHostController = navHostController
            )
        }
    }
}
