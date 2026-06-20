package com.laiana.motosync

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.laiana.motosync.data.local.database.MotoDatabase
import com.laiana.motosync.data.repository.RoomMotoRepository
import com.laiana.motosync.navigation.Routes
import com.laiana.motosync.presentation.details.DetailsScreen
import com.laiana.motosync.presentation.home.HomeScreen
import com.laiana.motosync.presentation.home.HomeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MotoSyncInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun limparBancoEPrepararViewModel() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val database = MotoDatabase.getDatabase(context)
        val repository = RoomMotoRepository(database.motoDao())

        // Limpa todas as motos antes do teste começar
        runBlocking {
            repository.removerTodasAsMotos()
        }

        viewModel = HomeViewModel(repository)
    }

    @Test
    fun fullHomeToDetailsFlow_test() {
        composeTestRule.setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Routes.HOME) {
                composable(Routes.HOME) {
                    HomeScreen(navController = navController, viewModel = viewModel)
                }
                composable(Routes.DETALHES_COM_ARGUMENTO) { backStackEntry ->
                    val motoId = backStackEntry.arguments
                        ?.getString(Routes.MOTO_ID)
                        ?.toIntOrNull() ?: return@composable
                    DetailsScreen(
                        motoId = motoId,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }

        // Aguarda o ViewModel carregar as 5 motos iniciais
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            viewModel.motos.value.size == 5
        }
        composeTestRule.waitForIdle()

        // Confirma que a tela inicial mostra pelo menos um card
        composeTestRule.onAllNodesWithTag("MotoCard")
            .onFirst().assertExists()

        // Clica em "Adicionar moto" e confirma que a lista de dados cresceu
        composeTestRule.onNodeWithTag("BotaoAdicionarMoto").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            viewModel.motos.value.size == 6
        }
        composeTestRule.waitForIdle()

        // Clica nos botões de filtro e confirma que não quebram a tela
        composeTestRule.onNodeWithTag("FiltroDisponivel").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithTag("MotoCard").onFirst().assertExists()

        composeTestRule.onNodeWithTag("FiltroAlugada").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("FiltroManutencao").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("FiltroTodos").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithTag("MotoCard").onFirst().assertExists()

        // Digita no campo de busca e confirma que o app não quebra
        composeTestRule.onNodeWithTag("CampoBusca").performTextInput("Honda")
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithTag("MotoCard").onFirst().assertExists()

        // Limpa o campo de busca
        composeTestRule.onNodeWithTag("CampoBusca").performTextClearance()
        composeTestRule.waitForIdle()

        // Clica em ordenar (ida e volta), garantindo que não crasha
        composeTestRule.onNodeWithTag("BotaoOrdenar").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("BotaoOrdenar").performClick()
        composeTestRule.waitForIdle()

        // Clica no primeiro card e confirma a navegação para a tela de detalhes
        composeTestRule.onAllNodesWithTag("MotoCard").onFirst().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Detalhes da Moto").assertIsDisplayed()

        // Confirma que o botão "Voltar" retorna pra Home
        composeTestRule.onNodeWithText("Voltar").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("BotaoAdicionarMoto").assertIsDisplayed()
    }
}