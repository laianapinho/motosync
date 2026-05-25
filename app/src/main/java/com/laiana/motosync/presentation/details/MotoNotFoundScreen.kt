package com.laiana.motosync.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Tela mostrada quando uma moto não é encontrada.
@Composable
fun MotoNotFoundScreen(navController: NavController) {

    // Cria layout vertical.
    Column(

        // Configura tamanho e espaçamento da tela.
        modifier = Modifier

            // Faz a tela ocupar todo o espaço disponível.
            .fillMaxSize()

            // Adiciona espaçamento interno.
            .padding(24.dp)
    ) {

        // Mostra a mensagem de erro.
        Text(
            text = "Moto não encontrada",
            style = MaterialTheme.typography.headlineLarge
        )

        // Cria espaço antes do botão.
        Spacer(modifier = Modifier.height(24.dp))

        // Cria botão para voltar.
        Button(

            // Define o clique do botão.
            onClick = {

                // Volta para a tela anterior.
                navController.popBackStack()
            },

            // Faz o botão ocupar toda a largura.
            modifier = Modifier.fillMaxWidth()
        ) {

            // Texto exibido no botão.
            Text(text = "Voltar")
        }
    }
}