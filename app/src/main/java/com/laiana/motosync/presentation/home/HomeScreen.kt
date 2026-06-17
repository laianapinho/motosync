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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.laiana.motosync.presentation.components.MotoCard
import com.laiana.motosync.presentation.components.StatusSummary
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import com.laiana.motosync.navigation.Routes
import com.laiana.motosync.domain.model.Moto

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    // Observa a lista de motos do StateFlow
    val motos by viewModel.motos.collectAsState()

    // Estado para controlar se a ordenação é crescente ou decrescente
    var crescente by remember { mutableStateOf(true) }

    // Lista ordenada dinamicamente
    val motosOrdenadas = remember(motos, crescente) {
        viewModel.ordernarMotoporQuilometragem(motos, crescente)
    }

    val motosDisponiveis = viewModel.contarMotosDisponiveis()
    val motosAlugadas = viewModel.contarMotosAlugadas()
    val motosEmManutencao = viewModel.contarMotosEmManutencao()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        Text(
            text = "MotoSync",
            style = MaterialTheme.typography.headlineLarge
        )

        StatusSummary(
            total = motos.size,
            disponiveis = motosDisponiveis,
            alugadas = motosAlugadas,
            emManutencao = motosEmManutencao
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.adicionarMotoFake() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Adicionar moto")
        }

        // Botão para alternar a ordenação
        Button(
            onClick = { crescente = !crescente }, // inverte crescente/decrescente
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (crescente) "Ordenar Decrescente" else "Ordenar Crescente")
        }

        Button(
            onClick = { viewModel.removerTodasAsMotos() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Remover todas as motos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(motosOrdenadas) { moto ->
                MotoCard(
                    moto = moto,
                    onDetalhesClick = { navController.navigate(Routes.detalhesComId(moto.id)) },
                    onAlterarStatusClick = { viewModel.alterarStatusDaMoto(moto.id) },
                    onRemoverClick = { viewModel.removerMoto(moto.id) }
                )
            }
        }
    }
}