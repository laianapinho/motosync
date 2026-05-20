package com.laiana.motosync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laiana.motosync.ui.theme.MotoSyncTheme
import com.laiana.motosync.domain.model.Moto

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MotoSyncTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val motos = listOf(
        Moto(
            id = 1,
            nome = "Honda Biz 125",
            modelo = "Urbana",
            placa = "ABC-1234",
            status = "Disponível"
        ),
        Moto(
            id = 2,
            nome = "Honda Pop 110i",
            modelo = "Econômica",
            placa = "DEF-5678",
            status = "Alugada"
        ),
        Moto(
            id = 3,
            nome = "Yamaha Factor 150",
            modelo = "Street",
            placa = "GHI-9012",
            status = "Manutenção"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MotoSync",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Total de motos: ${motos.size}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Primeira moto: ${motos[0].nome}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Status: ${motos[0].status}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MotoSyncTheme {
        HomeScreen()
    }
}