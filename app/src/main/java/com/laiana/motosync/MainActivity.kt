package com.laiana.motosync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.laiana.motosync.navigation.MotoSyncApp
import com.laiana.motosync.ui.theme.MotoSyncTheme
import dagger.hilt.android.AndroidEntryPoint

// Declara a Activity principal do aplicativo.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Método executado quando a Activity é criada.
    override fun onCreate(savedInstanceState: Bundle?) {

        // Chama o comportamento padrão da Activity.
        super.onCreate(savedInstanceState)

        // Define que a interface da tela será feita usando Jetpack Compose.
        setContent {

            // Aplica o tema visual do MotoSync.
            MotoSyncTheme {

                // Cria a superfície base da tela.
                Surface(

                    // Faz a superfície ocupar toda a tela.
                    modifier = Modifier.fillMaxSize(),

                    // Usa a cor de fundo definida no tema.
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Chama o componente principal do app, responsável pela navegação.
                    MotoSyncApp()
                }
            }
        }
    }
}