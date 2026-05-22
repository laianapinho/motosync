// Define o pacote principal do app.
// Esse pacote precisa ser o mesmo configurado no projeto Android.
package com.laiana.motosync

// Importa a classe Bundle, usada no ciclo de vida da Activity.
import android.os.Bundle

// Importa a ComponentActivity, que é a base da tela principal no Android moderno.
import androidx.activity.ComponentActivity

// Permite usar Jetpack Compose dentro da Activity.
import androidx.activity.compose.setContent

// Imports de layout do Jetpack Compose.
// Arrangement ajuda a organizar os elementos verticalmente ou horizontalmente.
import androidx.compose.foundation.layout.Arrangement

// Column organiza os componentes um embaixo do outro.
import androidx.compose.foundation.layout.Column

// Spacer cria espaços vazios entre os componentes.
import androidx.compose.foundation.layout.Spacer

// fillMaxSize faz o componente ocupar todo o espaço disponível.
import androidx.compose.foundation.layout.fillMaxSize

// fillMaxWidth faz o componente ocupar toda a largura disponível.
import androidx.compose.foundation.layout.fillMaxWidth

// height define uma altura para um componente, muito usado com Spacer.
import androidx.compose.foundation.layout.height

// padding adiciona espaçamento interno ou externo.
import androidx.compose.foundation.layout.padding

// Define cantos arredondados para componentes como Card.
import androidx.compose.foundation.shape.RoundedCornerShape

// Button cria um botão clicável.
import androidx.compose.material3.Button

// Card cria um bloco visual parecido com um cartão.
import androidx.compose.material3.Card

// CardDefaults permite configurar propriedades padrão do Card, como elevação.
import androidx.compose.material3.CardDefaults

// MaterialTheme acessa estilos do tema, como tipografia e cores.
import androidx.compose.material3.MaterialTheme

// Surface cria uma área base com cor de fundo e suporte ao Material Design.
import androidx.compose.material3.Surface

// Text exibe textos na interface.
import androidx.compose.material3.Text

// Indica que uma função pode desenhar interface usando Jetpack Compose.
import androidx.compose.runtime.Composable

// Alignment ajuda a alinhar os elementos na tela.
import androidx.compose.ui.Alignment

// Modifier permite configurar tamanho, espaçamento, clique, alinhamento etc.
import androidx.compose.ui.Modifier

// Preview permite visualizar a tela no Android Studio sem rodar o app.
import androidx.compose.ui.tooling.preview.Preview

// dp é a unidade de medida usada no Compose para espaçamentos e tamanhos.
import androidx.compose.ui.unit.dp

// Importa a classe Moto criada no pacote domain.model.
import com.laiana.motosync.domain.model.Moto

// Importa o tema visual do projeto.
import com.laiana.motosync.ui.theme.MotoSyncTheme

// MainActivity é a tela principal do aplicativo.
// Ela é executada quando o app abre.
class MainActivity : ComponentActivity() {

    // onCreate é chamado quando a Activity é criada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent define que a interface dessa tela será feita com Compose.
        setContent {

            // Aplica o tema visual do app.
            MotoSyncTheme {

                // Surface funciona como uma área base da tela.
                // Aqui ela ocupa a tela inteira e usa a cor de fundo do tema.
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

// @Composable indica que essa função cria interface visual.
// HomeScreen é a tela inicial do app.
@Composable
fun HomeScreen() {

    // Cria um objeto Moto com dados fixos.
    // Por enquanto, esses dados são fake.
    // Mais para frente, eles virão de uma lista, banco local ou API.
    val moto = Moto(
        id = 1,
        nome = "Honda Biz 125",
        modelo = "Urbana",
        placa = "ABC-1234",
        status = "Disponível"
    )

    // Column organiza os componentes verticalmente.
    Column(
        modifier = Modifier
            // Faz a Column ocupar toda a tela.
            .fillMaxSize()

            // Adiciona espaçamento de 24.dp nas bordas.
            .padding(24.dp),

        // Centraliza os elementos verticalmente.
        verticalArrangement = Arrangement.Center,

        // Centraliza os elementos horizontalmente.
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Exibe o título do app.
        Text(
            text = "MotoSync",
            style = MaterialTheme.typography.headlineLarge
        )

        // Cria um espaço vertical entre o título e o card.
        Spacer(modifier = Modifier.height(24.dp))

        // Exibe o card da moto.
        // A moto criada acima é enviada como parâmetro.
        MotoCard(moto = moto)
    }
}

// Componente visual responsável por mostrar os dados de uma moto.
// Ele recebe um objeto Moto como parâmetro.
@Composable
fun MotoCard(moto: Moto) {

    // Card cria um bloco visual com sombra e bordas arredondadas.
    Card(
        // O card ocupa toda a largura disponível.
        modifier = Modifier.fillMaxWidth(),

        // Define os cantos arredondados do card.
        shape = RoundedCornerShape(16.dp),

        // Define a sombra/elevação do card.
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        // Conteúdo interno do Card.
        // A Column organiza as informações da moto verticalmente.
        Column(
            // Espaçamento interno do card.
            modifier = Modifier.padding(16.dp)
        ) {

            // Mostra o nome da moto em destaque.
            Text(
                text = moto.nome,
                style = MaterialTheme.typography.titleLarge
            )

            // Espaço entre o nome e as informações.
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

            // Mostra o status atual da moto.
            Text(
                text = "Status: ${moto.status}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Espaço entre os textos e o botão.
            Spacer(modifier = Modifier.height(16.dp))

            // Botão para ver detalhes da moto.
            Button(
                // Ação executada quando o botão é clicado.
                // Por enquanto, apenas imprime uma mensagem no console.
                onClick = {
                    println("Clicou na moto ${moto.nome}")
                },

                // O botão ocupa toda a largura disponível.
                modifier = Modifier.fillMaxWidth()
            ) {
                // Texto exibido dentro do botão.
                Text(text = "Ver detalhes")
            }
        }
    }
}

// Preview permite visualizar a tela no Android Studio.
// Não é executado no app final, serve para desenvolvimento.
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    // Aplica o tema no preview.
    MotoSyncTheme {

        // Mostra a HomeScreen dentro do preview.
        HomeScreen()
    }
}