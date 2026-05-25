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

// Tela de detalhes da moto.
@Composable
fun DetailsScreen(
    moto: Moto,
    navController: NavController
) {

    // Cria a organização vertical da tela.
    Column(

        // Configura tamanho e espaçamento da tela.
        modifier = Modifier

            // Faz a tela ocupar todo o espaço disponível.
            .fillMaxSize()

            // Adiciona espaçamento interno na tela.
            .padding(24.dp)
    ) {

        // Mostra o título da tela.
        Text(
            text = "Detalhes da Moto",
            style = MaterialTheme.typography.headlineLarge
        )

        // Cria espaço entre o título e o card.
        Spacer(modifier = Modifier.height(24.dp))

        // Cria um card para agrupar os detalhes.
        Card(

            // Faz o card ocupar toda a largura.
            modifier = Modifier.fillMaxWidth(),

            // Define cantos arredondados.
            shape = RoundedCornerShape(16.dp),

            // Define sombra do card.
            elevation = CardDefaults.cardElevation(

                // Define elevação padrão.
                defaultElevation = 6.dp
            )
        ) {

            // Organiza os dados dentro do card.
            Column(

                // Adiciona espaçamento interno.
                modifier = Modifier.padding(16.dp)
            ) {

                // Mostra o nome da moto.
                Text(
                    text = moto.nome,
                    style = MaterialTheme.typography.titleLarge
                )

                // Cria espaço entre o nome e os dados.
                Spacer(modifier = Modifier.height(16.dp))

                // Mostra o id da moto.
                Text(
                    text = "ID: ${moto.id}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra o modelo da moto.
                Text(
                    text = "Modelo: ${moto.modelo}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra a placa da moto.
                Text(
                    text = "Placa: ${moto.placa}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra o ano da moto.
                Text(
                    text = "Ano: ${moto.ano}",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra a quilometragem da moto.
                Text(
                    text = "Quilometragem: ${moto.quilometragem} km",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra o status da moto.
                Text(
                    text = "Status: ${moto.status}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Cria espaço entre o card e o botão.
        Spacer(modifier = Modifier.height(24.dp))

        // Cria botão de voltar.
        Button(

            // Define a ação do botão.
            onClick = {

                // Volta para a tela anterior.
                navController.popBackStack()
            },

            // Faz o botão ocupar toda a largura.
            modifier = Modifier.fillMaxWidth()
        ) {

            // Texto do botão.
            Text(text = "Voltar")
        }
    }
}