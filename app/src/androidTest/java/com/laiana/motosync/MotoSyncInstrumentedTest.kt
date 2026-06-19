package com.laiana.motosync

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.laiana.motosync.presentation.home.HomeScreen
import com.laiana.motosync.presentation.home.HomeViewModel
import com.laiana.motosync.data.repository.FakeMotoRepository
import org.junit.Rule
import org.junit.Test

class MotoSyncInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun fullHomeToDetailsFlow_test() {
        val viewModel = HomeViewModel(FakeMotoRepository())
        val navController = rememberNavController()

        composeTestRule.setContent {
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        // Verifica se pelo menos um MotoCard aparece
        composeTestRule.onAllNodes(hasTestTag("MotoCard")).assertCountGreaterThan(0)

        // Adiciona uma moto fake e verifica lista
        composeTestRule.onNodeWithText("Adicionar moto").performClick()
        composeTestRule.onAllNodes(hasTestTag("MotoCard"))
            .assertCountEquals(viewModel.motos.value.size)

        // Filtra por status
        composeTestRule.onNodeWithText("Disponível").performClick()
        composeTestRule.onAllNodes(hasTestTag("MotoCard")).assertAll(
            hasAnyDescendant(hasText("Disponível"))
        )

        composeTestRule.onNodeWithText("Alugada").performClick()
        composeTestRule.onAllNodes(hasTestTag("MotoCard")).assertAll(
            hasAnyDescendant(hasText("Alugada"))
        )

        composeTestRule.onNodeWithText("Manutenção").performClick()
        composeTestRule.onAllNodes(hasTestTag("MotoCard")).assertAll(
            hasAnyDescendant(hasText("Manutenção"))
        )

        composeTestRule.onNodeWithText("Todos").performClick()
        composeTestRule.onAllNodes(hasTestTag("MotoCard"))
            .assertCountEquals(viewModel.motos.value.size)

        // Busca por nome/modelo
        composeTestRule.onNodeWithText("Buscar por nome ou modelo")
            .performTextInput("Honda")
        composeTestRule.onAllNodes(hasTestTag("MotoCard")).assertAll(
            hasAnyDescendant(hasText("Honda"))
        )

        // Ordenação crescente/decrescente
        composeTestRule.onNodeWithText("Ordenar Decrescente").performClick()
        composeTestRule.onNodeWithText("Ordenar Crescente").performClick()

        // Navegação para DetailsScreen
        composeTestRule.onAllNodes(hasTestTag("MotoCard")).onFirst().performClick()
        composeTestRule.onNodeWithText("Detalhes da Moto").assertIsDisplayed()
    }
}