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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.laiana.motosync.domain.model.Moto

@Composable
fun MotoCard(
    moto: Moto,
    onDetalhesClick: () -> Unit,
    onAlterarStatusClick: () -> Unit,
    onRemoverClick: () -> Unit,
    onAumentarKm500Click: (() -> Unit)? = null,   // Parâmetro opcional
    onAumentarKm1000Click: (() -> Unit)? = null   // Parâmetro opcional
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = moto.nome, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Modelo: ${moto.modelo}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Placa: ${moto.placa}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${moto.status}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quilometragem: ${moto.quilometragem} km", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onDetalhesClick, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Ver Detalhes")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onAlterarStatusClick, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Alterar Status")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onRemoverClick, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Remover Moto")
            }

            // Botões de quilometragem apenas se forem passados
            onAumentarKm500Click?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = it, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "+500 km")
                }
            }

            onAumentarKm1000Click?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = it, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "+1000 km")
                }
            }
        }
    }
}