package com.laiana.motosync.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.laiana.motosync.data.local.database.MotoDatabase
import com.laiana.motosync.data.repository.RoomMotoRepository
import com.laiana.motosync.presentation.details.DetailsScreen
import com.laiana.motosync.presentation.details.MotoNotFoundScreen
import com.laiana.motosync.presentation.home.HomeScreen
import com.laiana.motosync.presentation.home.HomeViewModel
import com.laiana.motosync.presentation.home.HomeViewModelFactory

// Função principal de navegação do aplicativo.
@Composable
fun MotoSyncApp() {

    // Cria o controlador de navegação
    val navController = rememberNavController()

    // Pega o contexto atual do Android
    val context = LocalContext.current

    // Cria ou recupera a instância do banco Room
    val database = remember {
        MotoDatabase.getDatabase(context)
    }

    // Cria o repository do Room usando o DAO do banco
    val repository = remember {
        RoomMotoRepository(database.motoDao())
    }

    // Cria o HomeViewModel usando uma Factory
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(repository)
    )

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