package com.laiana.motosync.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.laiana.motosync.domain.model.Moto
import androidx.compose.foundation.clickable

@Composable
fun MotoCard(
    moto: Moto,
    onDetalhesClick: () -> Unit,
    onAlterarStatusClick: () -> Unit,
    onRemoverClick: () -> Unit
) {
    // Anima a cor do badge de acordo com o status
    val targetColor = when (moto.status) {
        "Disponível" -> Color(0xFF4CAF50)
        "Alugada" -> Color(0xFFFFC107)
        "Manutenção" -> Color(0xFFF44336)
        else -> Color.Gray
    }
    val animatedColor by animateColorAsState(targetValue = targetColor)

    Card(
        //funcao que leva para a tela detalhes de cada moto
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetalhesClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = moto.nome,
                    style = MaterialTheme.typography.titleLarge
                )

                // Badge animado de status
                Box(
                    modifier = Modifier
                        .background(color = animatedColor, shape = RoundedCornerShape(8.dp))
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

            // Destacar a quilometragem com efeito visual simples quando muda
            var previousKm by remember { mutableStateOf(moto.quilometragem) }
            val quilometragemColor by animateColorAsState(
                targetValue = if (moto.quilometragem != previousKm) Color(0xFF2196F3) else Color.Unspecified
            )
            previousKm = moto.quilometragem

            Text(
                text = "Modelo: ${moto.modelo}", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Placa: ${moto.placa}", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Ano: ${moto.ano}", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Quilometragem: ${moto.quilometragem} km",
                style = MaterialTheme.typography.bodyMedium,
                color = quilometragemColor
            )
        }
    }
}