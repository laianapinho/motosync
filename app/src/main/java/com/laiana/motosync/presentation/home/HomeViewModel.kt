package com.laiana.motosync.presentation.home

import androidx.lifecycle.ViewModel
import com.laiana.motosync.domain.model.Moto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Cria o ViewModel da tela inicial.
// O ViewModel guarda e controla os dados usados pela HomeScreen.
class HomeViewModel : ViewModel() {

    // Cria uma lista inicial de motos.
    // Essa lista ainda é fake, mas agora será usada dentro de um StateFlow.
    private val listaInicialDeMotos = listOf(

        // Cria a primeira moto.
        Moto(
            id = 1,
            nome = "Honda Biz 125",
            modelo = "Urbana",
            placa = "ABC-1234",
            status = "Disponível",
            ano = 2022,
            quilometragem = 12500
        ),

        // Cria a segunda moto.
        Moto(
            id = 2,
            nome = "Honda Pop 110i",
            modelo = "Econômica",
            placa = "DEF-5678",
            status = "Alugada",
            ano = 2021,
            quilometragem = 18000
        ),

        // Cria a terceira moto.
        Moto(
            id = 3,
            nome = "Yamaha Factor 150",
            modelo = "Street",
            placa = "GHI-9012",
            status = "Manutenção",
            ano = 2020,
            quilometragem = 25000
        ),

        // Cria a quarta moto.
        Moto(
            id = 4,
            nome = "Honda CG 160",
            modelo = "Street",
            placa = "JKL-3456",
            status = "Disponível",
            ano = 2023,
            quilometragem = 8000
        ),

        // Cria a quinta moto.
        Moto(
            id = 5,
            nome = "Yamaha Fazer 250",
            modelo = "Street",
            placa = "MNO-7890",
            status = "Disponível",
            ano = 2022,
            quilometragem = 15000
        )
    )

    // Cria um StateFlow privado e mutável.
    // O underline em _motos indica que essa variável só deve ser alterada dentro do ViewModel.
    private val _motos = MutableStateFlow(listaInicialDeMotos)

    // Cria um StateFlow público e somente leitura.
    // A tela poderá observar motos, mas não poderá alterar a lista diretamente.
    val motos: StateFlow<List<Moto>> = _motos.asStateFlow()

    // Busca uma moto pelo id.
    // Essa função será usada pela tela de detalhes.
    fun buscarMotoPorId(id: Int?): Moto? {

        // Procura na lista atual do StateFlow uma moto com o id recebido.
        return _motos.value.find { moto ->

            // Compara o id da moto atual com o id recebido.
            moto.id == id
        }
    }

    // Conta quantas motos estão disponíveis.
    // Essa função mantém o mini desafio do Dia 8.
    fun contarMotosDisponiveis(): Int {

        // Conta as motos cujo status é "Disponível".
        return _motos.value.count { moto ->

            // Verifica se a moto atual está disponível.
            moto.status == "Disponível"
        }
    }

    // Conta quantas motos estão alugadas.
    // Essa função mantém o mini desafio do Dia 8.
    fun contarMotosAlugadas(): Int {

        // Conta as motos cujo status é "Alugada".
        return _motos.value.count { moto ->

            // Verifica se a moto atual está alugada.
            moto.status == "Alugada"
        }
    }

    // Conta quantas motos estão em manutenção.
    // Essa função mantém o mini desafio do Dia 8.
    fun contarMotosEmManutencao(): Int {

        // Conta as motos cujo status é "Manutenção".
        return _motos.value.count { moto ->

            // Verifica se a moto atual está em manutenção.
            moto.status == "Manutenção"
        }
    }

    // Altera o status de uma moto com base no id recebido.
    fun alterarStatusDaMoto(id: Int) {

        // Cria uma nova lista baseada na lista atual.
        // O map percorre cada moto da lista.
        val novaLista = _motos.value.map { moto ->

            // Verifica se a moto atual é a moto que deve ser alterada.
            if (moto.id == id) {

                // Define o próximo status da moto.
                val novoStatus = when (moto.status) {

                    // Se estiver disponível, muda para alugada.
                    "Disponível" -> "Alugada"

                    // Se estiver alugada, muda para manutenção.
                    "Alugada" -> "Manutenção"

                    // Se estiver em manutenção, muda para disponível.
                    "Manutenção" -> "Disponível"

                    // Se o status for desconhecido, mantém o mesmo status.
                    else -> moto.status
                }

                // Cria uma cópia da moto com o novo status.
                moto.copy(status = novoStatus)
            } else {

                // Se não for a moto escolhida, mantém a moto sem alteração.
                moto
            }
        }

        // Atualiza o StateFlow com a nova lista.
        // Ao fazer isso, a HomeScreen será atualizada automaticamente.
        _motos.value = novaLista
    }
}