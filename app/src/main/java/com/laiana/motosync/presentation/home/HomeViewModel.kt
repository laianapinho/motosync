package com.laiana.motosync.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laiana.motosync.data.repository.RoomMotoRepository
import com.laiana.motosync.domain.constants.MotoStatus
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.usecase.GerarMotoFakeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel da tela inicial do MotoSync
// Responsável por coordenar dados da tela e executar operações no banco via RoomMotoRepository
class HomeViewModel(
    private val repository: RoomMotoRepository // Recebe o repository que acessa o banco
) : ViewModel() {

    // Caso de uso para gerar uma nova moto fake
    private val gerarMotoFakeUseCase = GerarMotoFakeUseCase()

    // Observa a lista de motos do banco usando Flow, convertendo em StateFlow para Compose
    val motos: StateFlow<List<Moto>> = repository.observarMotos().stateIn(
        scope = viewModelScope,                       // Usa o escopo do ViewModel para manter a coroutine ativa
        started = SharingStarted.WhileSubscribed(5000), // Mantém o Flow ativo enquanto há assinantes
        initialValue = emptyList()                    // Valor inicial enquanto o banco ainda não respondeu
    )

    // Bloco inicial do ViewModel
    // Executado assim que o ViewModel é criado
    init {
        viewModelScope.launch {                          // Coroutine para executar operações de banco
            if (repository.bancoVazio()) {              // Verifica se o banco está vazio
                val motosIniciais = listOf(            // Cria lista inicial de motos
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
                repository.adicionarMotos(motosIniciais) // Adiciona motos iniciais no banco se estiver vazio
            }
        }
    }

    // Busca uma moto pelo id
    fun buscarMotoPorId(id: Int?): Moto? {
        if (id == null) return null                  // Retorna null se id não informado
        return motos.value.find { it.id == id }      // Procura a moto na lista atual observada
    }

    // Altera o status de uma moto (Disponível → Alugada → Manutenção → Disponível)
    fun alterarStatusDaMoto(id: Int) {
        viewModelScope.launch {                       // Executa operação de banco em coroutine
            repository.alterarStatusDaMoto(id)       // Pede ao repository para alterar status
        }
    }

    // Conta quantas motos estão disponíveis
    fun contarMotosDisponiveis(): Int =
        motos.value.count { it.status == MotoStatus.DISPONIVEL }

    // Conta quantas motos estão alugadas
    fun contarMotosAlugadas(): Int =
        motos.value.count { it.status == MotoStatus.ALUGADA }

    // Conta quantas motos estão em manutenção
    fun contarMotosEmManutencao(): Int =
        motos.value.count { it.status == MotoStatus.MANUTENCAO }

    // Adiciona uma moto fake no banco
    fun adicionarMotoFake() {
        val novaMoto = gerarMotoFakeUseCase(motos.value) // Gera nova moto fake
        viewModelScope.launch {                          // Executa operação de banco
            repository.adicionarMoto(novaMoto)          // Adiciona no banco via repository
        }
    }

    // Remove uma moto pelo id
    fun removerMoto(id: Int) {
        viewModelScope.launch {                          // Executa operação de banco
            repository.removerMoto(id)                  // Remove a moto via repository
        }
    }

    // Remove todas as motos do banco
    fun removerTodasAsMotos() {
        viewModelScope.launch {                          // Executa operação de banco
            repository.removerTodasAsMotos()           // Chama o repository que executa DELETE
        }
    }

    // Aumenta a quilometragem de uma moto em 1000 km
    fun aumentarQuilometragem(id: Int) {
        val motoAtual = buscarMotoPorId(id)             // Busca a moto na lista atual
        if (motoAtual != null) {
            val novaQuilometragem = motoAtual.quilometragem + 1000
            viewModelScope.launch {                     // Executa operação de banco
                repository.atualizarQuilometragem(
                    id = id,
                    novaQuilometragem = novaQuilometragem
                )
            }
        }
    }
}