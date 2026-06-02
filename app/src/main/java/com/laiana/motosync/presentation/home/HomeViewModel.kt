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

// ViewModel da tela inicial.
// Agora ele usa o RoomMotoRepository para buscar e alterar dados no banco local.
class HomeViewModel(
    private val repository: RoomMotoRepository
) : ViewModel() {

    // Cria o caso de uso responsável por gerar uma moto fake.
    private val gerarMotoFakeUseCase = GerarMotoFakeUseCase()

    // Observa a lista de motos que vem do Room.
    // O repository retorna Flow<List<Moto>>.
    // O stateIn transforma esse Flow em StateFlow para o Compose observar melhor.
    val motos: StateFlow<List<Moto>> = repository.observarMotos().stateIn(

        // Usa o escopo do ViewModel para manter a observação ativa.
        scope = viewModelScope,

        // Mantém o Flow ativo enquanto a tela estiver observando.
        started = SharingStarted.WhileSubscribed(5000),

        // Valor inicial enquanto o banco ainda não respondeu.
        initialValue = emptyList()
    )

    // Bloco executado quando o ViewModel é criado.
    init {

        // Chama a função que preenche o banco com dados iniciais.
        popularBancoInicial()
    }

    // Preenche o banco com motos iniciais.
    // Como o Room começa vazio, isso cria dados para a primeira execução.
    private fun popularBancoInicial() {

        // Executa operação de banco dentro de uma coroutine.
        viewModelScope.launch {

            // Cria uma lista inicial de motos.
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

            // Adiciona a lista inicial no banco.
            // Como usamos Insert com REPLACE, se a moto já existir, ela é substituída.
            repository.adicionarMotos(motosIniciais)
        }
    }

    // Busca uma moto pelo id usando a lista atual observada.
    fun buscarMotoPorId(id: Int?): Moto? {

        // Se o id for nulo, retorna nulo.
        if (id == null) {
            return null
        }

        // Procura a moto dentro da lista atual do StateFlow.
        return motos.value.find { moto ->

            // Compara o id da moto com o id recebido.
            moto.id == id
        }
    }

    // Altera o status de uma moto no banco.
    fun alterarStatusDaMoto(id: Int) {

        // Executa a operação de banco dentro de uma coroutine.
        viewModelScope.launch {

            // Pede ao repository para alterar o status da moto.
            repository.alterarStatusDaMoto(id)
        }
    }

    // Conta quantas motos estão disponíveis.
    fun contarMotosDisponiveis(): Int {

        // Conta na lista atual quantas motos possuem status disponível.
        return motos.value.count { moto ->

            // Verifica se o status é disponível.
            moto.status == MotoStatus.DISPONIVEL
        }
    }

    // Remove todas as motos
    fun removerTodasAsMotos() {

        // Executa operação de banco dentro de uma coroutine.
        viewModelScope.launch {

            // Pede ao repository para remover todas as motos.
            repository.removerTodasAsMotos()
        }
    }

    // Conta quantas motos estão alugadas.
    fun contarMotosAlugadas(): Int {

        // Conta na lista atual quantas motos possuem status alugada.
        return motos.value.count { moto ->

            // Verifica se o status é alugada.
            moto.status == MotoStatus.ALUGADA
        }
    }

    // Conta quantas motos estão em manutenção.
    fun contarMotosEmManutencao(): Int {

        // Conta na lista atual quantas motos possuem status manutenção.
        return motos.value.count { moto ->

            // Verifica se o status é manutenção.
            moto.status == MotoStatus.MANUTENCAO
        }
    }

    // Adiciona uma moto fake no banco.
    fun adicionarMotoFake() {

        // Gera uma nova moto fake usando a lista atual.
        val novaMoto = gerarMotoFakeUseCase(motos.value)

        // Executa operação de banco dentro de uma coroutine.
        viewModelScope.launch {

            // Adiciona a nova moto no banco.
            repository.adicionarMoto(novaMoto)
        }
    }

    // Remove uma moto pelo id.
    fun removerMoto(id: Int) {

        // Executa operação de banco dentro de uma coroutine.
        viewModelScope.launch {

            // Pede ao repository para remover a moto.
            repository.removerMoto(id)
        }
    }

    // Aumenta a quilometragem de uma moto em 1000 km.
    fun aumentarQuilometragem(id: Int) {

        // Busca a moto atual na lista observada.
        val motoAtual = buscarMotoPorId(id)

        // Verifica se a moto existe.
        if (motoAtual != null) {

            // Calcula a nova quilometragem.
            val novaQuilometragem = motoAtual.quilometragem + 1000

            // Executa operação de banco dentro de uma coroutine.
            viewModelScope.launch {

                // Atualiza a quilometragem no banco.
                repository.atualizarQuilometragem(
                    id = id,
                    novaQuilometragem = novaQuilometragem
                )
            }
        }
    }
}