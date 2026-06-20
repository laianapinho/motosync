package com.laiana.motosync.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laiana.motosync.domain.repository.MotoRepository
import com.laiana.motosync.domain.constants.MotoStatus
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.usecase.GerarMotoFakeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel da tela inicial do MotoSync
// Responsável por coordenar dados da tela e executar operações no banco via RoomMotoRepository
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MotoRepository
) : ViewModel() {

    // Caso de uso para gerar uma nova moto fake
    private val gerarMotoFakeUseCase = GerarMotoFakeUseCase()

    // Observa a lista de motos do banco usando Flow, convertendo em StateFlow para Compose
    val motos: StateFlow<List<Moto>> = repository.observarMotos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Bloco inicial do ViewModel
    init {
        viewModelScope.launch {
            if (repository.bancoVazio()) {
                val motosIniciais = listOf(
                    Moto(
                        id = 1,
                        nome = "Honda Biz 125",
                        modelo = "Urbana",
                        placa = "ABC-1234",
                        status = MotoStatus.DISPONIVEL,
                        ano = 2022,
                        quilometragem = 12500
                    ),
                    Moto(
                        id = 2,
                        nome = "Honda Pop 110i",
                        modelo = "Econômica",
                        placa = "DEF-5678",
                        status = MotoStatus.ALUGADA,
                        ano = 2021,
                        quilometragem = 18000
                    ),
                    Moto(
                        id = 3,
                        nome = "Yamaha Factor 150",
                        modelo = "Street",
                        placa = "GHI-9012",
                        status = MotoStatus.MANUTENCAO,
                        ano = 2020,
                        quilometragem = 25000
                    ),
                    Moto(
                        id = 4,
                        nome = "Honda CG 160",
                        modelo = "Street",
                        placa = "JKL-3456",
                        status = MotoStatus.DISPONIVEL,
                        ano = 2023,
                        quilometragem = 8000
                    ),
                    Moto(
                        id = 5,
                        nome = "Yamaha Fazer 250",
                        modelo = "Street",
                        placa = "MNO-7890",
                        status = MotoStatus.DISPONIVEL,
                        ano = 2022,
                        quilometragem = 15000
                    )
                )
                repository.adicionarMotos(motosIniciais)
            }
        }
    }

    // Busca uma moto pelo id
    fun buscarMotoPorId(id: Int?): Moto? {
        if (id == null) return null
        return motos.value.find { it.id == id }
    }

    // Altera o status de uma moto
    fun alterarStatusDaMoto(id: Int) {
        viewModelScope.launch {
            repository.alterarStatusDaMoto(id)
        }
    }

    fun contarMotosDisponiveis(): Int =
        motos.value.count { it.status == MotoStatus.DISPONIVEL }

    fun contarMotosAlugadas(): Int =
        motos.value.count { it.status == MotoStatus.ALUGADA }

    fun contarMotosEmManutencao(): Int =
        motos.value.count { it.status == MotoStatus.MANUTENCAO }

    fun adicionarMotoFake() {
        val novaMoto = gerarMotoFakeUseCase(motos.value)
        viewModelScope.launch {
            repository.adicionarMoto(novaMoto)
        }
    }

    fun removerMoto(id: Int) {
        viewModelScope.launch {
            repository.removerMoto(id)
        }
    }

    fun removerTodasAsMotos() {
        viewModelScope.launch {
            repository.removerTodasAsMotos()
        }
    }

    fun aumentarQuilometragem(id: Int, valor: Int) {
        val motoAtual = buscarMotoPorId(id)
        if (motoAtual != null) {
            val novaQuilometragem = motoAtual.quilometragem + valor
            viewModelScope.launch {
                repository.atualizarQuilometragem(
                    id = id,
                    novaQuilometragem = novaQuilometragem
                )
            }
        }
    }

    fun resetarQuilometragemMoto(id: Int) {
        val motoAtual = buscarMotoPorId(id)
        if (motoAtual != null) {
            viewModelScope.launch {
                repository.resetarQuilometragemMoto(
                    id = id
                )
            }
        }
    }

    fun ordernarMotoporQuilometragem(motos: List<Moto>, crescente: Boolean = true): List<Moto> {
        val novaLista = if (crescente) {
            motos.sortedBy { it.quilometragem }
        } else {
            motos.sortedByDescending { it.quilometragem }
        }
        return novaLista
    }

    fun filtrarMotosPorStatus(motos: List<Moto>, statusSelecionado: String): List<Moto> {
        if (statusSelecionado == "Todos") {
            return motos
        } else {
            return motos.filter { it.status == statusSelecionado }
        }
    }

    fun filtrarMotosPorNomeouModelo(motos: List<Moto>, nomeSelecionado: String, modeloSelecionado: String): List<Moto> {
        return (motos.filter { it.nome == nomeSelecionado && it.modelo == modeloSelecionado })
    }
}