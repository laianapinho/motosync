package com.laiana.motosync.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.laiana.motosync.domain.model.Moto

@Composable
fun MotoCard(
    moto: Moto,
    onDetalhesClick: () -> Unit,
    onAlterarStatusClick: () -> Unit,
    onRemoverClick: () -> Unit
) {
    // Define a cor de fundo do badge com base no status
    val statusColor = when (moto.status) {
        "Disponível" -> Color(0xFF4CAF50) // verde
        "Alugada" -> Color(0xFFFFC107)    // amarelo
        "Manutenção" -> Color(0xFFF44336) // vermelho
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = moto.nome,
                    style = MaterialTheme.typography.titleLarge
                )

                // Badge visualizando o status
                Box(
                    modifier = Modifier
                        .background(color = statusColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = moto.status,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Modelo: ${moto.modelo}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Placa: ${moto.placa}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Ano: ${moto.ano}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quilometragem: ${moto.quilometragem} km", style = MaterialTheme.typography.bodyMedium)
        }
    }
}