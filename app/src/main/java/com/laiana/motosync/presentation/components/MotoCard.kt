package com.laiana.motosync.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.laiana.motosync.domain.model.Moto

// Componente reutilizável para mostrar os dados resumidos de uma moto.
@Composable
fun MotoCard(
    moto: Moto,
    onDetalhesClick: () -> Unit,
    onAlterarStatusClick: () -> Unit
) {

    // Cria o estado local de favorito da moto.
    var isFavorita by remember {

        // Define que a moto começa como não favoritada.
        mutableStateOf(false)
    }

    // Cria o card visual da moto.
    Card(

        // Faz o card ocupar toda a largura disponível.
        modifier = Modifier.fillMaxWidth(),

        // Define os cantos arredondados do card.
        shape = RoundedCornerShape(16.dp),

        // Define a sombra do card.
        elevation = CardDefaults.cardElevation(

            // Define a elevação padrão.
            defaultElevation = 6.dp
        )
    ) {

        // Organiza o conteúdo interno do card na vertical.
        Column(

            // Adiciona espaçamento interno no card.
            modifier = Modifier.padding(16.dp)
        ) {

            // Mostra o nome da moto.
            Text(
                text = moto.nome,
                style = MaterialTheme.typography.titleLarge
            )

            // Cria espaço entre o nome e os dados.
            Spacer(modifier = Modifier.height(8.dp))

            // Mostra o modelo da moto.
            Text(
                text = "Modelo: ${moto.modelo}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Mostra a placa da moto.
            Text(
                text = "Placa: ${moto.placa}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Mostra o ano da moto.
            Text(
                text = "Ano: ${moto.ano}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Mostra o status da moto.
            Text(
                text = "Status: ${moto.status}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Mostra se a moto está favoritada.
            Text(
                text = if (isFavorita) "Favorita: Sim" else "Favorita: Não",
                style = MaterialTheme.typography.bodyMedium
            )

            // Cria espaço antes do botão de favoritar.
            Spacer(modifier = Modifier.height(16.dp))

            // Cria botão para favoritar ou desfavoritar.
            Button(

                // Define o clique do botão.
                onClick = {

                    // Inverte o estado de favorito.
                    isFavorita = !isFavorita
                },

                // Faz o botão ocupar toda a largura.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Mostra o texto conforme o estado de favorito.
                Text(
                    text = if (isFavorita) "Favoritado" else "Favoritar"
                )
            }

            // Cria espaço entre os botões.
            Spacer(modifier = Modifier.height(8.dp))

            // Cria botão para alterar o status da moto.
            Button(

                // Executa a ação recebida da HomeScreen.
                onClick = onAlterarStatusClick,

                // Faz o botão ocupar toda a largura.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Texto do botão.
                Text(text = "Alterar status")
            }

            // Cria espaço entre os botões.
            Spacer(modifier = Modifier.height(8.dp))

            // Cria botão para abrir os detalhes.
            Button(

                // Executa a ação recebida da HomeScreen.
                onClick = onDetalhesClick,

                // Faz o botão ocupar toda a largura.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Texto do botão.
                Text(text = "Ver detalhes")
            }
        }
    }
}