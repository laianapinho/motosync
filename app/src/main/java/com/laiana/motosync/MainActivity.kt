// Define o pacote principal do aplicativo.
// Esse pacote identifica onde a MainActivity está localizada.
package com.laiana.motosync

// Importa a classe Bundle, usada no ciclo de vida da Activity.
import android.os.Bundle

// ComponentActivity é a classe base usada para criar uma tela no Android moderno.
import androidx.activity.ComponentActivity

// Permite usar Jetpack Compose dentro da Activity.
import androidx.activity.compose.setContent

// Arrangement organiza os elementos dentro de layouts como Column e LazyColumn.
import androidx.compose.foundation.layout.Arrangement

// Column organiza os componentes na vertical, um abaixo do outro.
import androidx.compose.foundation.layout.Column

// PaddingValues permite definir espaçamentos internos em componentes como LazyColumn.
import androidx.compose.foundation.layout.PaddingValues

// Spacer cria espaços vazios entre componentes.
import androidx.compose.foundation.layout.Spacer

// fillMaxSize faz o componente ocupar todo o espaço disponível da tela.
import androidx.compose.foundation.layout.fillMaxSize

// fillMaxWidth faz o componente ocupar toda a largura disponível.
import androidx.compose.foundation.layout.fillMaxWidth

// height define uma altura para um componente.
import androidx.compose.foundation.layout.height

// padding adiciona espaçamento interno ou externo.
import androidx.compose.foundation.layout.padding

// LazyColumn cria uma lista vertical otimizada.
// Ela renderiza apenas os itens visíveis na tela, melhorando desempenho.
import androidx.compose.foundation.lazy.LazyColumn

// items permite percorrer uma lista dentro da LazyColumn.
import androidx.compose.foundation.lazy.items

// RoundedCornerShape define cantos arredondados para componentes.
import androidx.compose.foundation.shape.RoundedCornerShape

// Button cria um botão clicável.
import androidx.compose.material3.Button

// Card cria um bloco visual com aparência de cartão.
import androidx.compose.material3.Card

// CardDefaults permite configurar propriedades padrão do Card, como elevação.
import androidx.compose.material3.CardDefaults

// MaterialTheme permite acessar cores, tipografia e estilos do tema.
import androidx.compose.material3.MaterialTheme

// Surface cria uma base visual para a tela, respeitando o Material Design.
import androidx.compose.material3.Surface

// Text exibe textos na interface.
import androidx.compose.material3.Text

// @Composable permite criar funções de interface com Jetpack Compose.
import androidx.compose.runtime.Composable

// Modifier permite configurar tamanho, espaçamento, comportamento e aparência dos componentes.
import androidx.compose.ui.Modifier

// Preview permite visualizar a tela diretamente no Android Studio.
import androidx.compose.ui.tooling.preview.Preview

// dp é a unidade usada para definir medidas no Compose.
import androidx.compose.ui.unit.dp

// Importa a data class Moto, criada para representar os dados de uma moto.
import com.laiana.motosync.domain.model.Moto

// Importa o tema visual do projeto.
import com.laiana.motosync.ui.theme.MotoSyncTheme

// MainActivity é a tela principal do aplicativo.
// Ela é executada quando o app é aberto.
class MainActivity : ComponentActivity() {

    // onCreate é chamado quando a Activity é criada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent define que a interface da tela será criada com Jetpack Compose.
        setContent {

            // Aplica o tema visual do app.
            MotoSyncTheme {

                // Surface funciona como a base visual da tela.
                // Aqui ela ocupa a tela inteira e usa a cor de fundo definida no tema.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Chama a tela inicial do app.
                    HomeScreen()
                }
            }
        }
    }
}

// HomeScreen é a tela inicial do MotoSync.
// Ela exibe o título do app, a quantidade de motos e uma lista de cards.
@Composable
fun HomeScreen() {

    // Cria uma lista fixa de motos.
    // Por enquanto, esses dados são fake.
    // Mais para frente, essa lista virá de ViewModel, banco Room ou API.
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
        ),
        Moto(
            id = 4,
            nome = "Honda CG 160",
            modelo = "Street",
            placa = "JKL-3456",
            status = "Disponível"
        ),
        Moto(
            id = 5,
            nome = "Yamaha Fazer 250",
            modelo = "Street",
            placa = "MNO-7890",
            status = "Disponível"
        )
    )

    // Column organiza os elementos principais da tela na vertical.
    Column(
        modifier = Modifier
            // Faz a Column ocupar toda a tela.
            .fillMaxSize()

            // Adiciona espaçamento nas laterais e no topo/baixo.
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // Título principal do aplicativo.
        Text(
            text = "MotoSync",
            style = MaterialTheme.typography.headlineLarge
        )

        // Texto mostrando a quantidade de motos cadastradas.
        // motos.size retorna o tamanho da lista.
        Text(
            text = "Motos cadastradas: ${motos.size}",
            style = MaterialTheme.typography.bodyLarge
        )

        // Espaço entre o cabeçalho e a lista.
        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn cria uma lista vertical com rolagem.
        // É ideal para listas porque carrega os itens de forma eficiente.
        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            // Adiciona um espaço extra no final da lista.
            contentPadding = PaddingValues(bottom = 16.dp),

            // Define espaçamento vertical entre cada card da lista.
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Percorre a lista de motos.
            // Para cada moto encontrada, cria um MotoCard.
            items(motos) { moto ->
                MotoCard(moto = moto)
            }
        }
    }
}

// MotoCard é um componente reutilizável.
// Ele recebe uma Moto e exibe seus dados dentro de um Card.
@Composable
fun MotoCard(moto: Moto) {

    // Card cria um bloco visual com sombra e cantos arredondados.
    Card(
        // O card ocupa toda a largura disponível da tela.
        modifier = Modifier.fillMaxWidth(),

        // Define o arredondamento das bordas do card.
        shape = RoundedCornerShape(16.dp),

        // Define a elevação/sombra do card.
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        // Column organiza os textos e o botão dentro do card.
        Column(
            // Espaçamento interno do card.
            modifier = Modifier.padding(16.dp)
        ) {

            // Exibe o nome da moto em destaque.
            Text(
                text = moto.nome,
                style = MaterialTheme.typography.titleLarge
            )

            // Espaço entre o nome e as informações.
            Spacer(modifier = Modifier.height(8.dp))

            // Exibe o modelo da moto.
            Text(
                text = "Modelo: ${moto.modelo}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Exibe a placa da moto.
            Text(
                text = "Placa: ${moto.placa}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Exibe o status atual da moto.
            Text(
                text = "Status: ${moto.status}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Espaço entre os textos e o botão.
            Spacer(modifier = Modifier.height(16.dp))

            // Botão para acessar detalhes da moto.
            Button(
                // Ação executada quando o botão é clicado.
                // Por enquanto, apenas imprime uma mensagem no console.
                onClick = {
                    println("Clicou na moto ${moto.nome}")
                },

                // O botão ocupa toda a largura do card.
                modifier = Modifier.fillMaxWidth()
            ) {

                // Texto exibido dentro do botão.
                Text(text = "Ver detalhes")
            }
        }
    }
}

// Preview permite visualizar a tela no Android Studio.
// Ele não altera o funcionamento do app final.
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    // Aplica o tema também no preview.
    MotoSyncTheme {

        // Exibe a HomeScreen na pré-visualização.
        HomeScreen()
    }
}