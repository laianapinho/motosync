package com.laiana.motosync.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.presentation.home.HomeViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// Tela de detalhes da moto
@Composable
fun DetailsScreen(
    motoId: Int,
    viewModel: HomeViewModel,
    navController: NavController
) {
    // Observa a lista reativa e busca a moto por id
    val motos by viewModel.motos.collectAsState()
    val moto = motos.find { it.id == motoId } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Detalhes da Moto",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = moto.nome, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "ID: ${moto.id}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Modelo: ${moto.modelo}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Placa: ${moto.placa}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Ano: ${moto.ano}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Quilometragem: ${moto.quilometragem} km", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Status: ${moto.status}", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão para zerar quilometragem
        Button(
            onClick = {
                viewModel.resetarQuilometragemMoto(moto.id)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Zerar Quilometragem")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de voltar
        Button(
            onClick = {
                navController.popBackStack() // Apenas navega, não altera a moto
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
        }
    }
}