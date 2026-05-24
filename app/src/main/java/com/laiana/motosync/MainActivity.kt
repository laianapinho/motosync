package com.laiana.motosync
import androidx.compose.runtime.collectAsState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.presentation.home.HomeViewModel
import com.laiana.motosync.ui.theme.MotoSyncTheme

// Declara a classe principal do aplicativo.
class MainActivity : ComponentActivity() {

    // Método chamado quando a tela principal do app é criada.
    override fun onCreate(savedInstanceState: Bundle?) {

        // Chama o comportamento padrão da Activity.
        super.onCreate(savedInstanceState)

        // Define que a interface do app será feita usando Jetpack Compose.
        setContent {

            // Aplica o tema visual do aplicativo.
            MotoSyncTheme {

                // Cria uma superfície base para a tela.
                Surface(

                    // Faz a Surface ocupar toda a tela.
                    modifier = Modifier.fillMaxSize(),

                    // Define a cor de fundo usando o tema do app.
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Chama a função principal do aplicativo.
                    MotoSyncApp()
                }
            }
        }
    }
}

// Função principal do app.
// Ela configura a navegação entre as telas.
@Composable
fun MotoSyncApp() {

    // Cria o controlador de navegação.
    // Ele é responsável por controlar a troca de telas.
    val navController = rememberNavController()

    // Cria o ViewModel da tela inicial.
    // Agora a lista de motos fica no ViewModel, não mais diretamente na HomeScreen.
    val homeViewModel: HomeViewModel = viewModel()

    // Cria o NavHost.
    // O NavHost é o container que guarda as rotas/telas do app.
    NavHost(

        // Informa qual controlador de navegação será usado.
        navController = navController,

        // Define que a primeira tela aberta será a HomeScreen.
        startDestination = "home"
    ) {

        // Define a rota da tela inicial.
        composable("home") {

            // Chama a tela inicial.
            // Agora ela recebe o navController e o ViewModel.
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel
            )
        }

        // Define a rota da tela de detalhes.
        // O trecho {motoId} indica que essa rota recebe o id da moto.
        composable("detalhes/{motoId}") { backStackEntry ->

            // Recupera o id da moto que foi enviado pela rota.
            val motoId = backStackEntry.arguments
                ?.getString("motoId")
                ?.toIntOrNull()

            // Usa o ViewModel para buscar a moto pelo id recebido.
            val motoSelecionada = homeViewModel.buscarMotoPorId(motoId)

            // Verifica se a moto foi encontrada.
            if (motoSelecionada != null) {

                // Se encontrou a moto, abre a tela de detalhes.
                DetailsScreen(
                    moto = motoSelecionada,
                    navController = navController
                )
            } else {

                // Se não encontrou a moto, mostra uma tela de erro simples.
                MotoNotFoundScreen(navController = navController)
            }
        }
    }
}

// Tela inicial do aplicativo.
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {

    // Observa a lista de motos que vem do ViewModel.
    // O collectAsState transforma o StateFlow em estado do Compose.
    val motos by viewModel.motos.collectAsState()

    // Busca no ViewModel a quantidade de motos disponíveis.
    val motosDisponiveis = viewModel.contarMotosDisponiveis()

    // Busca no ViewModel a quantidade de motos alugadas.
    val motosAlugadas = viewModel.contarMotosAlugadas()

    // Busca no ViewModel a quantidade de motos em manutenção.
    val motosEmManutencao = viewModel.contarMotosEmManutencao()

    // Cria uma coluna para organizar os elementos verticalmente.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // Mostra o título principal do app.
        Text(
            text = "MotoSync",
            style = MaterialTheme.typography.headlineLarge
        )

        // Mostra a quantidade total de motos cadastradas.
        Text(
            text = "Motos cadastradas: ${motos.size}",
            style = MaterialTheme.typography.bodyLarge
        )

        // Mostra quantas motos estão disponíveis.
        Text(
            text = "Disponíveis: $motosDisponiveis",
            style = MaterialTheme.typography.bodyLarge
        )

        // Mostra quantas motos estão alugadas.
        Text(
            text = "Alugadas: $motosAlugadas",
            style = MaterialTheme.typography.bodyLarge
        )

        // Mostra quantas motos estão em manutenção.
        Text(
            text = "Em manutenção: $motosEmManutencao",
            style = MaterialTheme.typography.bodyLarge
        )

        // Cria um espaço entre o cabeçalho e a lista.
        Spacer(modifier = Modifier.height(16.dp))

        // Cria uma lista vertical com rolagem.
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Percorre a lista de motos.
            items(motos) { moto ->

                // Cria um card para cada moto.
                MotoCard(
                    moto = moto,
                    onDetalhesClick = {
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

        // Define a sombra/elevação do card.
        elevation = CardDefaults.cardElevation(

            // Define a elevação padrão do card.
            defaultElevation = 6.dp
        )
    ) {

        // Organiza os dados da moto dentro do card.
        Column(

            // Adiciona espaçamento interno dentro do card.
            modifier = Modifier.padding(16.dp)
        ) {

            // Mostra o nome da moto.
            Text(
                text = moto.nome,
                style = MaterialTheme.typography.titleLarge
            )

            // Cria espaço entre o nome e os dados da moto.
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

            // Mostra se a moto está favoritada ou não.
            Text(
                text = if (isFavorita) "Favorita: Sim" else "Favorita: Não",
                style = MaterialTheme.typography.bodyMedium
            )

            // Cria espaço antes do botão de favoritar.
            Spacer(modifier = Modifier.height(16.dp))

            // Cria o botão de favoritar.
            Button(

                // Define o que acontece quando o botão é clicado.
                onClick = {

                    // Inverte o estado de favorito.
                    isFavorita = !isFavorita
                },

                // Faz o botão ocupar toda a largura do card.
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

                // Executa a ação recebida por parâmetro.
                onClick = onDetalhesClick,

                // Faz o botão ocupar toda a largura do card.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Texto exibido dentro do botão.
                Text(text = "Ver detalhes")
            }
        }
    }
}

// Tela de detalhes da moto.
@Composable
fun DetailsScreen(
    moto: Moto,
    navController: NavController
) {

    // Cria uma coluna para organizar os elementos da tela.
    Column(

        // Configura o tamanho e o espaçamento da tela.
        modifier = Modifier

            // Faz a tela ocupar todo o espaço disponível.
            .fillMaxSize()

            // Adiciona espaçamento ao redor da tela.
            .padding(24.dp)
    ) {

        // Mostra o título da tela.
        Text(
            text = "Detalhes da Moto",
            style = MaterialTheme.typography.headlineLarge
        )

        // Cria espaço entre o título e o card.
        Spacer(modifier = Modifier.height(24.dp))

        // Cria um card para agrupar os dados da moto.
        Card(

            // Faz o card ocupar toda a largura da tela.
            modifier = Modifier.fillMaxWidth(),

            // Define cantos arredondados no card.
            shape = RoundedCornerShape(16.dp),

            // Define a sombra/elevação do card.
            elevation = CardDefaults.cardElevation(

                // Define a elevação padrão.
                defaultElevation = 6.dp
            )
        ) {

            // Organiza os dados da moto dentro do card.
            Column(

                // Adiciona espaçamento interno no card.
                modifier = Modifier.padding(16.dp)
            ) {

                // Mostra o nome da moto.
                Text(
                    text = moto.nome,
                    style = MaterialTheme.typography.titleLarge
                )

                // Cria espaço entre o nome e as informações.
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

        // Cria espaço entre o card e o botão de voltar.
        Spacer(modifier = Modifier.height(24.dp))

        // Cria o botão de voltar.
        Button(

            // Define a ação do botão.
            onClick = {

                // Volta para a tela anterior.
                navController.popBackStack()
            },

            // Faz o botão ocupar toda a largura.
            modifier = Modifier.fillMaxWidth()
        ) {

            // Texto exibido dentro do botão.
            Text(text = "Voltar")
        }
    }
}

// Tela exibida quando uma moto não é encontrada.
@Composable
fun MotoNotFoundScreen(navController: NavController) {

    // Cria uma coluna para organizar os elementos da tela.
    Column(

        // Configura tamanho e espaçamento.
        modifier = Modifier

            // Faz a tela ocupar todo o espaço disponível.
            .fillMaxSize()

            // Adiciona espaçamento ao redor da tela.
            .padding(24.dp)
    ) {

        // Mostra mensagem de erro.
        Text(
            text = "Moto não encontrada",
            style = MaterialTheme.typography.headlineLarge
        )

        // Cria espaço antes do botão.
        Spacer(modifier = Modifier.height(24.dp))

        // Cria botão para voltar.
        Button(

            // Volta para a tela anterior.
            onClick = {
                navController.popBackStack()
            },

            // Faz o botão ocupar toda a largura.
            modifier = Modifier.fillMaxWidth()
        ) {

            // Texto exibido dentro do botão.
            Text(text = "Voltar")
        }
    }
}

// Cria uma prévia da tela inicial no Android Studio.
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    // Aplica o tema visual do app na prévia.
    MotoSyncTheme {

        // Cria um ViewModel para ser usado na prévia.
        val homeViewModel: HomeViewModel = viewModel()

        // Cria um controlador de navegação para ser usado na prévia.
        val navController = rememberNavController()

        // Mostra a HomeScreen na prévia.
        HomeScreen(
            navController = navController,
            viewModel = homeViewModel
        )
    }
}