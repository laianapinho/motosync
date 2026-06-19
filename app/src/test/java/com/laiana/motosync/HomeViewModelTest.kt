package com.laiana.motosync.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.laiana.motosync.data.repository.FakeMotoRepository
import com.laiana.motosync.domain.constants.MotoStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(repository = FakeMotoRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verifica lista inicial de motos`() = runTest {
        val collectJob = viewModel.motos.onEach { }.launchIn(this)
        advanceUntilIdle()

        val motos = viewModel.motos.value
        assertEquals(5, motos.size)
        assertEquals(MotoStatus.DISPONIVEL, motos[0].status)

        collectJob.cancel()
    }

    @Test
    fun `adicionar moto fake aumenta tamanho da lista`() = runTest {
        val collectJob = viewModel.motos.onEach { }.launchIn(this)
        advanceUntilIdle()

        val tamanhoAntes = viewModel.motos.value.size
        viewModel.adicionarMotoFake()
        advanceUntilIdle()
        val tamanhoDepois = viewModel.motos.value.size
        assertEquals(tamanhoAntes + 1, tamanhoDepois)

        collectJob.cancel()
    }

    @Test
    fun `alterar status de uma moto funciona`() = runTest {
        val collectJob = viewModel.motos.onEach { }.launchIn(this)
        advanceUntilIdle()

        val moto = viewModel.motos.value.first { it.status == MotoStatus.DISPONIVEL }
        viewModel.alterarStatusDaMoto(moto.id)
        advanceUntilIdle()
        val motoAtualizada = viewModel.motos.value.find { it.id == moto.id }
        assertEquals(MotoStatus.ALUGADA, motoAtualizada?.status)

        collectJob.cancel()
    }

    @Test
    fun `remover moto diminui lista`() = runTest {
        val collectJob = viewModel.motos.onEach { }.launchIn(this)
        advanceUntilIdle()

        val moto = viewModel.motos.value.first()
        val tamanhoAntes = viewModel.motos.value.size
        viewModel.removerMoto(moto.id)
        advanceUntilIdle()
        val tamanhoDepois = viewModel.motos.value.size
        assertEquals(tamanhoAntes - 1, tamanhoDepois)

        collectJob.cancel()
    }
}