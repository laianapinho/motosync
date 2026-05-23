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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.ui.theme.MotoSyncTheme

// Declara a classe principal do aplicativo.
class MainActivity : ComponentActivity() {

    // Método executado quando a tela principal é criada.
    override fun onCreate(savedInstanceState: Bundle?) {

        // Chama o comportamento padrão da Activity.
        super.onCreate(savedInstanceState)

        // Define que a interface será criada com Jetpack Compose.
        setContent {

            // Aplica o tema visual do MotoSync.
            MotoSyncTheme {

                // Cria a superfície base da tela.
                Surface(

                    // Faz a superfície ocupar toda a tela.
                    modifier = Modifier.fillMaxSize(),

                    // Define a cor de fundo usando o tema do app.
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Chama o componente responsável pela navegação do app.
                    MotoSyncApp()
                }
            }
        }
    }
}

// Função principal de navegação do aplicativo.
@Composable
fun MotoSyncApp() {

    // Cria e memoriza o controlador de navegação.
    // Esse objeto permite trocar de uma tela para outra.
    val navController = rememberNavController()

    // Cria o NavHost, que é o container das telas navegáveis.
    NavHost(

        // Informa qual controlador de navegação será usado.
        navController = navController,

        // Define qual tela abre primeiro.
        startDestination = "home"
    ) {

        // Define a rota da tela inicial.
        composable("home") {

            // Mostra a tela inicial.
            HomeScreen(navController = navController)
        }

        // Define a rota da tela de detalhes.
        // O trecho {motoId} indica que a rota recebe o id da moto.
        composable("detalhes/{motoId}") { backStackEntry ->

            // Recupera o id enviado pela tela inicial.
            val motoId = backStackEntry.arguments?.getString("motoId")?.toIntOrNull()

            // Busca a moto correspondente ao id recebido.
            val motoSelecionada = getMotosFake().find { moto -> moto.id == motoId }

            // Verifica se a moto foi encontrada.
            if (motoSelecionada != null) {

                // Mostra a tela de detalhes com a moto encontrada.
                DetailsScreen(
                    moto = motoSelecionada,
                    navController = navController
                )
            } else {

                // Mostra uma tela simples caso o id não encontre nenhuma moto.
                MotoNotFoundScreen(navController = navController)
            }
        }
    }
}

// Função que retorna uma lista fake de motos.
fun getMotosFake(): List<Moto> {

    // Retorna uma lista fixa de motos.
    return listOf(

        // Cria a primeira moto da lista.
        Moto(
            id = 1,
            nome = "Honda Biz 125",
            modelo = "Urbana",
            placa = "ABC-1234",
            status = "Disponível",
            ano = 2022,
            quilometragem = 12500
        ),

        // Cria a segunda moto da lista.
        Moto(
            id = 2,
            nome = "Honda Pop 110i",
            modelo = "Econômica",
            placa = "DEF-5678",
            status = "Alugada",
            ano = 2021,
            quilometragem = 18000
        ),

        // Cria a terceira moto da lista.
        Moto(
            id = 3,
            nome = "Yamaha Factor 150",
            modelo = "Street",
            placa = "GHI-9012",
            status = "Manutenção",
            ano = 2020,
            quilometragem = 25000
        ),

        // Cria a quarta moto da lista.
        Moto(
            id = 4,
            nome = "Honda CG 160",
            modelo = "Street",
            placa = "JKL-3456",
            status = "Disponível",
            ano = 2023,
            quilometragem = 8000
        ),

        // Cria a quinta moto da lista.
        Moto(
            id = 5,
            nome = "Yamaha Fazer 250",
            modelo = "Street",
            placa = "MNO-7890",
            status = "Disponível",
            ano = 2022,
            quilometragem = 15000
        )
    )
}

// Tela inicial do aplicativo.
@Composable
fun HomeScreen(navController: NavController) {

    // Busca a lista fake de motos.
    val motos = getMotosFake()

    // Cria um layout vertical para a tela.
    Column(

        // Configura o tamanho e o espaçamento da tela.
        modifier = Modifier

            // Faz a tela ocupar todo o espaço disponível.
            .fillMaxSize()

            // Adiciona espaçamento nas laterais e nas bordas superior/inferior.
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // Mostra o título do aplicativo.
        Text(
            text = "MotoSync",
            style = MaterialTheme.typography.headlineLarge
        )

        // Mostra a quantidade de motos cadastradas.
        Text(
            text = "Motos cadastradas: ${motos.size}",
            style = MaterialTheme.typography.bodyLarge
        )

        // Cria um espaço entre o cabeçalho e a lista.
        Spacer(modifier = Modifier.height(16.dp))

        // Cria uma lista vertical com rolagem.
        LazyColumn(

            // Faz a lista ocupar o espaço disponível.
            modifier = Modifier.fillMaxSize(),

            // Adiciona espaço no final da lista.
            contentPadding = PaddingValues(bottom = 16.dp),

            // Define espaçamento entre os cards.
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Percorre a lista de motos.
            items(motos) { moto ->

                // Mostra um card para cada moto da lista.
                MotoCard(

                    // Envia a moto atual para o card.
                    moto = moto,

                    // Define o que acontece quando clicar em Ver detalhes.
                    onDetalhesClick = {

                        // Navega para a tela de detalhes passando o id da moto.
                        navController.navigate("detalhes/${moto.id}")
                    }
                )
            }
        }
    }
}

// Componente visual que mostra os dados resumidos de uma moto.
@Composable
fun MotoCard(
    moto: Moto,
    onDetalhesClick: () -> Unit
) {

    // Cria uma variável de estado para controlar se a moto está favoritada.
    var isFavorita by remember {

        // Define que a moto começa como não favoritada.
        mutableStateOf(false)
    }

    // Cria um card visual.
    Card(

        // Faz o card ocupar toda a largura disponível.
        modifier = Modifier.fillMaxWidth(),

        // Define cantos arredondados no card.
        shape = RoundedCornerShape(16.dp),

        // Define a sombra do card.
        elevation = CardDefaults.cardElevation(

            // Define a elevação padrão.
            defaultElevation = 6.dp
        )
    ) {

        // Organiza o conteúdo do card na vertical.
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

            // Cria o botão de favoritar.
            Button(

                // Define a ação do clique.
                onClick = {

                    // Inverte o valor de favorito.
                    isFavorita = !isFavorita
                },

                // Faz o botão ocupar toda a largura.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Mostra o texto do botão conforme o estado.
                Text(
                    text = if (isFavorita) "Favoritado" else "Favoritar"
                )
            }

            // Cria espaço entre os botões.
            Spacer(modifier = Modifier.height(8.dp))

            // Cria o botão de ver detalhes.
            Button(

                // Usa a ação recebida por parâmetro.
                onClick = onDetalhesClick,

                // Faz o botão ocupar toda a largura.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Texto exibido dentro do botão.
                Text(text = "Ver detalhes")
            }
        }
    }
}

// Indica que essa função cria uma interface visual usando Jetpack Compose.
@Composable

// Declara a tela de detalhes da moto.
// Essa tela recebe uma moto e o navController para conseguir voltar para a tela anterior.
fun DetailsScreen(
    moto: Moto,
    navController: NavController
) {

    // Cria uma coluna para organizar os elementos verticalmente.
    Column(

        // Configura o tamanho e o espaçamento da tela.
        modifier = Modifier

            // Faz a tela ocupar todo o espaço disponível.
            .fillMaxSize()

            // Adiciona espaçamento interno em volta da tela.
            .padding(24.dp)
    ) {

        // Exibe o título da tela de detalhes.
        Text(

            // Texto que aparece no topo da tela.
            text = "Detalhes da Moto",

            // Usa o estilo de título grande do MaterialTheme.
            style = MaterialTheme.typography.headlineLarge
        )

        // Cria um espaço entre o título e o card de detalhes.
        Spacer(modifier = Modifier.height(24.dp))

        // Cria um card para agrupar as informações da moto.
        Card(

            // Faz o card ocupar toda a largura disponível.
            modifier = Modifier.fillMaxWidth(),

            // Define os cantos arredondados do card.
            shape = RoundedCornerShape(16.dp),

            // Define a sombra/elevação do card.
            elevation = CardDefaults.cardElevation(

                // Define a elevação padrão do card.
                defaultElevation = 6.dp
            )
        ) {

            // Organiza as informações da moto dentro do card.
            Column(

                // Adiciona espaçamento interno dentro do card.
                modifier = Modifier.padding(16.dp)
            ) {

                // Exibe o nome da moto em destaque.
                Text(

                    // Mostra o nome da moto recebida por parâmetro.
                    text = moto.nome,

                    // Usa estilo de título para destacar o nome.
                    style = MaterialTheme.typography.titleLarge
                )

                // Cria espaço entre o nome da moto e os dados seguintes.
                Spacer(modifier = Modifier.height(16.dp))

                // Mostra o identificador da moto.
                Text(

                    // Exibe o id da moto.
                    text = "ID: ${moto.id}",

                    // Usa estilo de texto grande.
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra o modelo da moto.
                Text(

                    // Exibe o modelo da moto.
                    text = "Modelo: ${moto.modelo}",

                    // Usa estilo de texto grande.
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra a placa da moto.
                Text(

                    // Exibe a placa da moto.
                    text = "Placa: ${moto.placa}",

                    // Usa estilo de texto grande.
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra o status da moto.
                Text(

                    // Exibe o status atual da moto.
                    text = "Status: ${moto.status}",

                    // Usa estilo de texto grande.
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra o ano da moto.
                Text(

                    // Exibe o ano da moto.
                    text = "Ano: ${moto.ano}",

                    // Usa estilo de texto grande.
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostra a quilometragem da moto.
                Text(

                    // Exibe a quilometragem da moto.
                    text = "Quilometragem: ${moto.quilometragem} km",

                    // Usa estilo de texto grande.
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Cria espaço entre o card e o botão de voltar.
        Spacer(modifier = Modifier.height(24.dp))

        // Cria o botão para voltar para a tela anterior.
        Button(

            // Define o que acontece quando o botão é clicado.
            onClick = {

                // Volta para a tela anterior na pilha de navegação.
                navController.popBackStack()
            },

            // Faz o botão ocupar toda a largura da tela.
            modifier = Modifier.fillMaxWidth()
        ) {

            // Texto exibido dentro do botão.
            Text(text = "Voltar")
        }
    }
}

// Tela exibida caso a moto não seja encontrada.
@Composable
fun MotoNotFoundScreen(navController: NavController) {

    // Cria um layout vertical.
    Column(

        // Configura tamanho e espaçamento.
        modifier = Modifier

            // Ocupa toda a tela.
            .fillMaxSize()

            // Adiciona espaçamento.
            .padding(24.dp)
    ) {

        // Mostra mensagem de erro.
        Text(
            text = "Moto não encontrada",
            style = MaterialTheme.typography.headlineLarge
        )

        // Cria espaço antes do botão.
        Spacer(modifier = Modifier.height(24.dp))

        // Botão para voltar.
        Button(

            // Volta para a tela anterior.
            onClick = {
                navController.popBackStack()
            },

            // Ocupa toda a largura.
            modifier = Modifier.fillMaxWidth()
        ) {

            // Texto do botão.
            Text(text = "Voltar")
        }
    }
}

// Cria uma prévia da tela inicial no Android Studio.
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    // Aplica o tema do MotoSync na prévia.
    MotoSyncTheme {

        // Cria um navController apenas para a prévia.
        val navController = rememberNavController()

        // Mostra a tela inicial na prévia.
        HomeScreen(navController = navController)
    }
}