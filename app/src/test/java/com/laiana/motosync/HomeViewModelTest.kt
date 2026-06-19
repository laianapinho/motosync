package com.laiana.motosync.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.laiana.motosync.domain.constants.MotoStatus
import com.laiana.motosync.domain.repository.FakeMotoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    // Permite que LiveData/StateFlow execute instantaneamente
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        // Inicializa o ViewModel usando o FakeMotoRepository do pacote correto
        viewModel = HomeViewModel(repository = FakeMotoRepository())
    }

    @Test
    fun `verifica lista inicial de motos`() = runTest {
        val motos = viewModel.motos.value
        assertEquals(5, motos.size)
        assertEquals(MotoStatus.DISPONIVEL, motos[0].status)
    }

    @Test
    fun `adicionar moto fake aumenta tamanho da lista`() = runTest {
        val tamanhoAntes = viewModel.motos.value.size
        viewModel.adicionarMotoFake()
        val tamanhoDepois = viewModel.motos.value.size
        assertEquals(tamanhoAntes + 1, tamanhoDepois)
    }

    @Test
    fun `alterar status de uma moto funciona`() = runTest {
        val moto = viewModel.motos.value.first { it.status == MotoStatus.DISPONIVEL }
        viewModel.alterarStatusDaMoto(moto.id)
        val motoAtualizada = viewModel.motos.value.find { it.id == moto.id }
        assertEquals(MotoStatus.ALUGADA, motoAtualizada?.status)
    }

    @Test
    fun `remover moto diminui lista`() = runTest {
        val moto = viewModel.motos.value.first()
        val tamanhoAntes = viewModel.motos.value.size
        viewModel.removerMoto(moto.id)
        val tamanhoDepois = viewModel.motos.value.size
        assertEquals(tamanhoAntes - 1, tamanhoDepois)
    }
}