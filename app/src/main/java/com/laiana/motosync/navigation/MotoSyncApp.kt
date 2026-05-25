package com.laiana.motosync.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.laiana.motosync.presentation.details.DetailsScreen
import com.laiana.motosync.presentation.details.MotoNotFoundScreen
import com.laiana.motosync.presentation.home.HomeScreen
import com.laiana.motosync.presentation.home.HomeViewModel

// Função principal do app.
// Ela controla a navegação entre as telas.
@Composable
fun MotoSyncApp() {

    // Cria o controlador de navegação.
    val navController = rememberNavController()

    // Cria o ViewModel da tela inicial.
    val homeViewModel: HomeViewModel = viewModel()

    // Cria o container de navegação do aplicativo.
    NavHost(

        // Define qual controlador de navegação será usado.
        navController = navController,

        // Define a tela inicial do app.
        startDestination = "home"
    ) {

        // Define a rota da tela inicial.
        composable("home") {

            // Mostra a HomeScreen.
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel
            )
        }

        // Define a rota da tela de detalhes.
        composable("detalhes/{motoId}") { backStackEntry ->

            // Recupera o id da moto enviado pela rota.
            val motoId = backStackEntry.arguments
                ?.getString("motoId")
                ?.toIntOrNull()

            // Busca a moto selecionada no ViewModel.
            val motoSelecionada = homeViewModel.buscarMotoPorId(motoId)

            // Verifica se a moto foi encontrada.
            if (motoSelecionada != null) {

                // Mostra a tela de detalhes da moto.
                DetailsScreen(
                    moto = motoSelecionada,
                    navController = navController
                )
            } else {

                // Mostra a tela de erro caso a moto não exista.
                MotoNotFoundScreen(navController = navController)
            }
        }
    }
}