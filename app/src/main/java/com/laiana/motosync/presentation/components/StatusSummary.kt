package com.laiana.motosync.presentation.components
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StatusSummary(
    total: Int,
    disponiveis: Int,
    alugadas: Int,
    emManutencao: Int
) {
    // Cria uma coluna para organizar os elementos verticalmente.
    Column{
        // Mostra a quantidade total de motos cadastradas.
        Text(
            text = "Motos cadastradas: $total",
            style = MaterialTheme.typography.bodyLarge
        )

        // Mostra quantas motos estão disponíveis.
        Text(
            text = "Disponíveis: $disponiveis",
            style = MaterialTheme.typography.bodyLarge
        )

        // Mostra quantas motos estão alugadas.
        Text(
            text = "Alugadas: $alugadas",
            style = MaterialTheme.typography.bodyLarge
        )

        // Mostra quantas motos estão em manutenção.
        Text(
            text = "Em manutenção: $emManutencao",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}