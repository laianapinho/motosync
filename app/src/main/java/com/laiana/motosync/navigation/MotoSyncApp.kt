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

    // Cria o controlador de navegação.
    val navController = rememberNavController()

    // Pega o contexto atual do Android.
    val context = LocalContext.current

    // Cria ou recupera a instância do banco Room.
    val database = remember {

        // Usa o contexto para criar o banco local.
        MotoDatabase.getDatabase(context)
    }

    // Cria o repository do Room usando o DAO do banco.
    val repository = remember {

        // Passa o MotoDao para o RoomMotoRepository.
        RoomMotoRepository(database.motoDao())
    }

    // Cria o HomeViewModel usando uma Factory.
    val homeViewModel: HomeViewModel = viewModel(

        // Passa a factory que sabe criar o HomeViewModel com repository.
        factory = HomeViewModelFactory(repository)
    )

    // Cria o container de navegação.
    NavHost(

        // Define o controlador de navegação.
        navController = navController,

        // Define a tela inicial.
        startDestination = Routes.HOME
    ) {

        // Define a rota da tela inicial.
        composable(Routes.HOME) {

            // Mostra a tela inicial.
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel
            )
        }

        // Define a rota da tela de detalhes com argumento.
        composable(Routes.DETALHES_COM_ARGUMENTO) { backStackEntry ->

            // Recupera o id enviado na rota.
            val motoId = backStackEntry.arguments
                ?.getString(Routes.MOTO_ID)
                ?.toIntOrNull()

            // Busca a moto selecionada pelo id.
            val motoSelecionada = homeViewModel.buscarMotoPorId(motoId)

            // Verifica se a moto foi encontrada.
            if (motoSelecionada != null) {

                // Mostra a tela de detalhes da moto.
                DetailsScreen(
                    moto = motoSelecionada,
                    navController = navController
                )
            } else {

                // Mostra a tela de erro caso a moto não seja encontrada.
                MotoNotFoundScreen(navController = navController)
            }
        }
    }
}