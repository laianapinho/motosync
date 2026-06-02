package com.laiana.motosync.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.laiana.motosync.presentation.components.MotoCard
import com.laiana.motosync.presentation.components.StatusSummary
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.navigation.compose.composable
import com.laiana.motosync.navigation.Routes

// Tela inicial do aplicativo.
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {

    // Observa a lista de motos vinda do ViewModel.
    val motos by viewModel.motos.collectAsState()

    // Busca a quantidade de motos disponíveis.
    val motosDisponiveis = viewModel.contarMotosDisponiveis()

    // Busca a quantidade de motos alugadas.
    val motosAlugadas = viewModel.contarMotosAlugadas()

    // Busca a quantidade de motos em manutenção.
    val motosEmManutencao = viewModel.contarMotosEmManutencao()

    // Cria o layout principal da tela em formato vertical.
    Column(

        // Configura tamanho e espaçamento da tela.
        modifier = Modifier

            // Faz a tela ocupar todo o espaço disponível.
            .fillMaxSize()

            // Adiciona espaçamento nas laterais, topo e base.
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // Mostra o título do aplicativo.
        Text(
            text = "MotoSync",
            style = MaterialTheme.typography.headlineLarge
        )

        // Mostra o resumo da frota usando um componente separado.
        StatusSummary(
            total = motos.size,
            disponiveis = motosDisponiveis,
            alugadas = motosAlugadas,
            emManutencao = motosEmManutencao
        )

        // Cria espaço entre o resumo e o botão de adicionar.
        Spacer(modifier = Modifier.height(12.dp))

        // Cria botão para adicionar uma nova moto fake.
        Button(

            // Quando clicar, pede ao ViewModel para adicionar uma moto fake.
            onClick = {
                viewModel.adicionarMotoFake()
            },

            // Faz o botão ocupar toda a largura disponível.
            modifier = Modifier.fillMaxWidth()
        ) {

            // Texto exibido dentro do botão.
            Text(text = "Adicionar moto")
        }

        // Cria botão para remover todas as motos.
        Button(

            // Quando clicar, pede ao ViewModel para remover todas as motos.
            onClick = {
                viewModel.removerTodasAsMotos()
            },

            // Faz o botão ocupar toda a largura disponível.
            modifier = Modifier.fillMaxWidth()
        ) {

            // Texto exibido dentro do botão.
            Text(text = "Remover todas as motos")
        }

        // Cria espaço entre o resumo e a lista.
        Spacer(modifier = Modifier.height(16.dp))

        // Cria uma lista vertical com rolagem.
        LazyColumn(

            // Faz a lista ocupar o espaço disponível.
            modifier = Modifier.fillMaxSize(),

            // Adiciona espaço no final da lista.
            contentPadding = PaddingValues(bottom = 16.dp),

            // Define espaçamento entre os cards.
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Percorre a lista de motos.
            items(motos) { moto ->

                // Cria um card para cada moto.
                MotoCard(

                    // Envia a moto atual para o card.
                    moto = moto,

                    // Define a ação do botão de detalhes.
                    onDetalhesClick = {

                        // Navega para a tela de detalhes da moto selecionada.
                        navController.navigate(Routes.detalhesComId(moto.id))
                    },

                    // Define a ação do botão de alterar status.
                    onAlterarStatusClick = {

                        // Pede ao ViewModel para alterar o status da moto.
                        viewModel.alterarStatusDaMoto(moto.id)
                    },

                    // Define a ação do botão de remover moto.
                    onRemoverClick = {

                        // Pede ao ViewModel para remover a moto atual.
                        viewModel.removerMoto(moto.id)
                    },

                    // Define a ação do botão de aumentar quilometragem.
                    onAumentarKmClick = {

                        // Pede ao ViewModel para aumentar a quilometragem da moto atual.
                        viewModel.aumentarQuilometragem(moto.id)
                    }
                )
            }
        }
    }
}