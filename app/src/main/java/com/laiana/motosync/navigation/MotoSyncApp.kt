package com.laiana.motosync.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.laiana.motosync.presentation.details.DetailsScreen
import com.laiana.motosync.presentation.home.HomeScreen
import com.laiana.motosync.presentation.home.HomeViewModel

// Função principal de navegação do aplicativo.
@Composable
fun MotoSyncApp() {

    // Cria o controlador de navegação
    val navController = rememberNavController()

    // Pede ao Hilt um HomeViewModel já pronto (com o repository já injetado)
    val homeViewModel: HomeViewModel = hiltViewModel()

    // Container de navegação
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        // Tela inicial
        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel
            )
        }

        // Tela de detalhes com argumento (id da moto)
        composable(Routes.DETALHES_COM_ARGUMENTO) { backStackEntry ->

            // Recupera o id enviado na rota
            val motoId = backStackEntry.arguments
                ?.getString(Routes.MOTO_ID)
                ?.toIntOrNull() ?: return@composable

            // Chama a tela de detalhes passando id e ViewModel
            DetailsScreen(
                motoId = motoId,
                viewModel = homeViewModel,
                navController = navController
            )
        }
    }
}