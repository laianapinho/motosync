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
import androidx.compose.material3.OutlinedTextField
import com.laiana.motosync.navigation.Routes
import com.laiana.motosync.domain.model.Moto

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val motos by viewModel.motos.collectAsState()

    // Estados de ordenação e filtros
    var crescente by remember { mutableStateOf(true) }
    var filtroStatus by remember { mutableStateOf("Todos") }
    var filtroNomeouModelo by remember { mutableStateOf("") } // inicialmente vazio

    // Lista final combinando ordenação + filtro de status + filtro de busca
    val listaFinal = remember(motos, crescente, filtroStatus, filtroNomeouModelo) {
        viewModel.ordernarMotoporQuilometragem(motos, crescente)
            .let { lista ->
                // Filtro por status
                val listaPorStatus = if (filtroStatus == "Todos") lista
                else lista.filter { it.status == filtroStatus }

                // Filtro por nome/modelo (apenas se houver termo digitado)
                if (filtroNomeouModelo.isNotEmpty()) {
                    listaPorStatus.filter {
                        it.nome.contains(filtroNomeouModelo, ignoreCase = true) ||
                                it.modelo.contains(filtroNomeouModelo, ignoreCase = true)
                    }
                } else {
                    listaPorStatus
                }
            }
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

        // Botão adicionar moto fake
        Button(onClick = { viewModel.adicionarMotoFake() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Adicionar moto")
        }

        // Alternar ordem crescente/decrescente
        Button(onClick = { crescente = !crescente }, modifier = Modifier.fillMaxWidth()) {
            Text(text = if (crescente) "Ordenar Decrescente" else "Ordenar Crescente")
        }

        // Filtros por status
        Button(onClick = { filtroStatus = "Todos" }, modifier = Modifier.fillMaxWidth()) { Text("Todos") }
        Button(onClick = { filtroStatus = "Disponível" }, modifier = Modifier.fillMaxWidth()) { Text("Disponível") }
        Button(onClick = { filtroStatus = "Alugada" }, modifier = Modifier.fillMaxWidth()) { Text("Alugada") }
        Button(onClick = { filtroStatus = "Manutenção" }, modifier = Modifier.fillMaxWidth()) { Text("Manutenção") }

        // Campo de busca por nome ou modelo
        OutlinedTextField(
            value = filtroNomeouModelo,
            onValueChange = { filtroNomeouModelo = it },
            label = { Text("Buscar por nome ou modelo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Botão remover todas motos
        Button(onClick = { viewModel.removerTodasAsMotos() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Remover todas as motos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn exibindo lista final
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaFinal) { moto ->
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