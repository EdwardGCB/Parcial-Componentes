package com.ud.planificador.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ud.planificador.views.AddView
import com.ud.planificador.views.InfoView
import com.ud.planificador.views.PrincipalView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable ("home") {
            PrincipalView(navController)
        }
        composable (
            "viaje/{viajeId}",
            arguments = listOf(navArgument("viajeId"){ type = NavType.IntType}
            )
        ) { backStackEntry ->
            val viajeId = backStackEntry.arguments?.getInt("viajeId")
            viajeId?.let { InfoView(navController, it) }
        }
        composable (
            "viaje/add/{viajeId}",
            arguments = listOf(navArgument("viajeId"){ type = NavType.IntType}
            )
        ) { backStackEntry ->
            val viajeId = backStackEntry.arguments?.getInt("viajeId")
            viajeId?.let { AddView(navController, it) }
        }

    }
}