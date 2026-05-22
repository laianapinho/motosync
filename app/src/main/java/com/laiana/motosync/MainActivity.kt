package com.laiana.motosync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.ui.theme.MotoSyncTheme

// Declara a classe principal do aplicativo.
// MainActivity é a primeira tela executada quando o app abre.
// Ela herda de ComponentActivity, uma Activity compatível com Jetpack Compose.
class MainActivity : ComponentActivity() {

    // Sobrescreve o método onCreate.
    // Esse método é chamado automaticamente quando a tela é criada.
    override fun onCreate(savedInstanceState: Bundle?) {

        // Chama a implementação original do onCreate da classe pai.
        super.onCreate(savedInstanceState)

        // Define o conteúdo visual da tela usando Jetpack Compose.
        setContent {

            // Aplica o tema visual do aplicativo MotoSync.
            MotoSyncTheme {

                // Cria uma superfície base para a tela.
                // Surface serve como um container visual que respeita o Material Design.
                Surface(

                    // Faz a Surface ocupar todo o tamanho disponível da tela.
                    modifier = Modifier.fillMaxSize(),

                    // Define a cor de fundo usando a cor configurada no tema do app.
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Chama a função composable responsável pela tela inicial.
                    HomeScreen()
                }
            }
        }
    }
}

// Indica que a função abaixo é um componente visual do Jetpack Compose.
@Composable

// Declara a função HomeScreen.
// Essa função representa a tela inicial do aplicativo.
fun HomeScreen() {

    // Cria uma lista fixa de motos.
    // Por enquanto, os dados são falsos/fake.
    // Futuramente, esses dados podem vir de banco local, API ou ViewModel.
    val motos = listOf(

        // Cria o primeiro objeto Moto da lista.
        Moto(

            // Define o identificador único da moto.
            id = 1,

            // Define o nome da moto.
            nome = "Honda Biz 125",

            // Define o modelo ou categoria da moto.
            modelo = "Urbana",

            // Define a placa da moto.
            placa = "ABC-1234",

            // Define o status atual da moto.
            status = "Disponível"
        ),

        // Cria o segundo objeto Moto da lista.
        Moto(

            // Define o identificador único da moto.
            id = 2,

            // Define o nome da moto.
            nome = "Honda Pop 110i",

            // Define o modelo ou categoria da moto.
            modelo = "Econômica",

            // Define a placa da moto.
            placa = "DEF-5678",

            // Define o status atual da moto.
            status = "Alugada"
        ),

        // Cria o terceiro objeto Moto da lista.
        Moto(

            // Define o identificador único da moto.
            id = 3,

            // Define o nome da moto.
            nome = "Yamaha Factor 150",

            // Define o modelo ou categoria da moto.
            modelo = "Street",

            // Define a placa da moto.
            placa = "GHI-9012",

            // Define o status atual da moto.
            status = "Manutenção"
        ),

        // Cria o quarto objeto Moto da lista.
        Moto(

            // Define o identificador único da moto.
            id = 4,

            // Define o nome da moto.
            nome = "Honda CG 160",

            // Define o modelo ou categoria da moto.
            modelo = "Street",

            // Define a placa da moto.
            placa = "JKL-3456",

            // Define o status atual da moto.
            status = "Disponível"
        ),

        // Cria o quinto objeto Moto da lista.
        Moto(

            // Define o identificador único da moto.
            id = 5,

            // Define o nome da moto.
            nome = "Yamaha Fazer 250",

            // Define o modelo ou categoria da moto.
            modelo = "Street",

            // Define a placa da moto.
            placa = "MNO-7890",

            // Define o status atual da moto.
            status = "Disponível"
        )
    )

    // Cria um layout vertical.
    // Tudo dentro da Column fica organizado de cima para baixo.
    Column(

        // Configura o tamanho e o espaçamento da Column.
        modifier = Modifier

            // Faz a Column ocupar toda a tela.
            .fillMaxSize()

            // Adiciona espaçamento horizontal de 24.dp e vertical de 16.dp.
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // Exibe o título principal do aplicativo.
        Text(

            // Define o texto que aparece na tela.
            text = "MotoSync",

            // Usa o estilo de título grande definido no tema.
            style = MaterialTheme.typography.headlineLarge
        )

        // Exibe a quantidade de motos cadastradas.
        Text(

            // Mostra o tamanho da lista de motos.
            // motos.size retorna a quantidade de itens da lista.
            text = "Motos cadastradas: ${motos.size}",

            // Usa o estilo de texto padrão grande.
            style = MaterialTheme.typography.bodyLarge
        )

        // Cria um espaço vertical entre o cabeçalho e a lista.
        Spacer(modifier = Modifier.height(16.dp))

        // Cria uma lista vertical com rolagem.
        // LazyColumn é recomendada para listas porque renderiza os itens de forma eficiente.
        LazyColumn(

            // Faz a lista ocupar o espaço restante da tela.
            modifier = Modifier.fillMaxSize(),

            // Adiciona um espaço no final da lista.
            contentPadding = PaddingValues(bottom = 16.dp),

            // Define um espaçamento de 12.dp entre cada item da lista.
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Percorre a lista de motos.
            // Para cada moto da lista, cria um card visual.
            items(motos) { moto ->

                // Chama o componente MotoCard passando a moto atual.
                MotoCard(moto = moto)
            }
        }
    }
}

// Indica que a função abaixo é um componente visual do Jetpack Compose.
@Composable

// Declara o componente MotoCard.
// Esse componente recebe uma moto e mostra seus dados em formato de card.
fun MotoCard(moto: Moto) {

    // Cria uma variável de estado chamada isFavorita.
    // Ela controla se a moto está favoritada ou não.
    var isFavorita by remember {

        // Define o valor inicial como false.
        // Ou seja, a moto começa como não favoritada.
        mutableStateOf(false)
    }

    // Cria um Card visual.
    // Card é um bloco com aparência de cartão.
    Card(

        // Faz o Card ocupar toda a largura disponível.
        modifier = Modifier.fillMaxWidth(),

        // Define bordas arredondadas de 16.dp.
        shape = RoundedCornerShape(16.dp),

        // Define a sombra/elevação do Card.
        elevation = CardDefaults.cardElevation(

            // Define a elevação padrão como 6.dp.
            defaultElevation = 6.dp
        )
    ) {

        // Organiza o conteúdo interno do Card na vertical.
        Column(

            // Adiciona espaçamento interno de 16.dp dentro do Card.
            modifier = Modifier.padding(16.dp)
        ) {

            // Exibe o nome da moto.
            Text(

                // Usa o campo nome do objeto moto.
                text = moto.nome,

                // Usa o estilo de título grande para destacar o nome.
                style = MaterialTheme.typography.titleLarge
            )

            // Cria um espaço vertical entre o nome e os demais dados.
            Spacer(modifier = Modifier.height(8.dp))

            // Exibe o modelo da moto.
            Text(

                // Monta o texto usando o campo modelo da moto.
                text = "Modelo: ${moto.modelo}",

                // Usa o estilo de texto médio.
                style = MaterialTheme.typography.bodyMedium
            )

            // Exibe a placa da moto.
            Text(

                // Monta o texto usando o campo placa da moto.
                text = "Placa: ${moto.placa}",

                // Usa o estilo de texto médio.
                style = MaterialTheme.typography.bodyMedium
            )

            // Exibe o status da moto.
            Text(

                // Monta o texto usando o campo status da moto.
                text = "Status: ${moto.status}",

                // Usa o estilo de texto médio.
                style = MaterialTheme.typography.bodyMedium
            )

            // Exibe se a moto está favoritada ou não.
            Text(

                // Se isFavorita for true, mostra "Favorita: Sim".
                // Se for false, mostra "Favorita: Não".
                text = if (isFavorita) "Favorita: Sim" else "Favorita: Não",

                // Usa o estilo de texto médio.
                style = MaterialTheme.typography.bodyMedium
            )

            // Cria um espaço entre os textos e o botão de favoritar.
            Spacer(modifier = Modifier.height(16.dp))

            // Cria o botão de favoritar.
            Button(

                // Define a ação executada quando o botão é clicado.
                onClick = {

                    // Inverte o valor de isFavorita.
                    // Se estava false, vira true.
                    // Se estava true, vira false.
                    isFavorita = !isFavorita
                },

                // Faz o botão ocupar toda a largura do Card.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Exibe o texto do botão.
                Text(

                    // Se a moto estiver favoritada, mostra "Favoritado".
                    // Caso contrário, mostra "Favoritar".
                    text = if (isFavorita) "Favoritado" else "Favoritar"
                )
            }

            // Cria um espaço entre o botão de favoritar e o botão de detalhes.
            Spacer(modifier = Modifier.height(8.dp))

            // Cria o botão de ver detalhes.
            Button(

                // Define a ação executada quando o botão é clicado.
                onClick = {

                    // Imprime uma mensagem no console informando a moto clicada.
                    println("Clicou na moto ${moto.nome}")
                },

                // Faz o botão ocupar toda a largura do Card.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Define o texto exibido dentro do botão.
                Text(text = "Ver detalhes")
            }
        }
    }
}

// Cria uma pré-visualização da tela dentro do Android Studio.
@Preview(showBackground = true)

// Indica que a função abaixo é um componente visual do Compose.
@Composable

// Declara a função de preview da HomeScreen.
fun HomeScreenPreview() {

    // Aplica o tema do MotoSync no preview.
    MotoSyncTheme {

        // Mostra a tela inicial dentro da pré-visualização.
        HomeScreen()
    }
}