package com.laiana.motosync.data.repository

import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.laiana.motosync.domain.constants.MotoStatus

// Repositório falso usado enquanto ainda não temos banco de dados ou API.
class FakeMotoRepository : MotoRepository {

    // Lista inicial de motos usada para simular dados reais.
    private val listaInicialDeMotos = listOf(

        // Primeira moto da lista.
        Moto(
            id = 1,
            nome = "Honda Biz 125",
            modelo = "Urbana",
            placa = "ABC-1234",
            status = MotoStatus.DISPONIVEL,
            ano = 2022,
            quilometragem = 12500
        ),

        // Segunda moto da lista.
        Moto(
            id = 2,
            nome = "Honda Pop 110i",
            modelo = "Econômica",
            placa = "DEF-5678",
            status = MotoStatus.ALUGADA,
            ano = 2021,
            quilometragem = 18000
        ),

        // Terceira moto da lista.
        Moto(
            id = 3,
            nome = "Yamaha Factor 150",
            modelo = "Street",
            placa = "GHI-9012",
            status = MotoStatus.MANUTENCAO,
            ano = 2020,
            quilometragem = 25000
        ),

        // Quarta moto da lista.
        Moto(
            id = 4,
            nome = "Honda CG 160",
            modelo = "Street",
            placa = "JKL-3456",
            status = MotoStatus.DISPONIVEL,
            ano = 2023,
            quilometragem = 8000
        ),

        // Quinta moto da lista.
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

    // StateFlow privado e mutável.
    // Só o repositório pode alterar essa lista.
    private val _motos = MutableStateFlow(listaInicialDeMotos)

    // StateFlow público e somente leitura.
    // ViewModel e tela podem observar, mas não alterar diretamente.
    override val motos: StateFlow<List<Moto>> = _motos.asStateFlow()

    // Busca uma moto pelo id recebido.
    override fun buscarMotoPorId(id: Int?): Moto? {

        // Procura na lista atual uma moto com o mesmo id.
        return _motos.value.find { moto ->

            // Compara o id da moto atual com o id recebido.
            moto.id == id
        }
    }

    // Altera o status de uma moto.
    override fun alterarStatusDaMoto(id: Int) {

        // Cria uma nova lista baseada na lista atual.
        val novaLista = _motos.value.map { moto ->

            // Verifica se a moto atual é a moto clicada.
            if (moto.id == id) {

                // Define o novo status com base no status atual da moto.
                val novoStatus = when (moto.status) {

                    // Se a moto estiver disponível, ela passa para alugada.
                    MotoStatus.DISPONIVEL -> MotoStatus.ALUGADA

                    // Se a moto estiver alugada, ela passa para manutenção.
                    MotoStatus.ALUGADA -> MotoStatus.MANUTENCAO

                    // Se a moto estiver em manutenção, ela volta para disponível.
                    MotoStatus.MANUTENCAO -> MotoStatus.DISPONIVEL

                    // Caso o status seja desconhecido, mantém o mesmo status.
                    else -> moto.status
                }

                // Cria uma cópia da moto com o novo status.
                moto.copy(status = novoStatus)
            } else {

                // Mantém as outras motos sem alteração.
                moto
            }
        }

        // Atualiza o StateFlow com a nova lista.
        _motos.value = novaLista
    }

    // Conta motos com base no status recebido.
    override fun contarMotosPorStatus(status: String): Int {

            // Percorre a lista atual e conta as motos com o status informado.
            return _motos.value.count { moto ->

                // Compara o status da moto atual com o status recebido.
                moto.status == status
            }

    }

    // Adiciona uma nova moto na lista atual.
    override fun adicionarMoto(moto: Moto) {

        // Cria uma nova lista pegando a lista atual e adicionando a nova moto no final.
        val novaLista = _motos.value + moto

        // Atualiza o StateFlow com a nova lista.
        _motos.value = novaLista
    }

    // Remove uma moto da lista usando o id recebido.
    override fun removerMoto(id: Int) {

        // Cria uma nova lista removendo a moto que possui o id informado.
        val novaLista = _motos.value.filter { moto ->

            // Mantém na lista apenas as motos com id diferente do id recebido.
            moto.id != id
        }

        // Atualiza o StateFlow com a nova lista.
        _motos.value = novaLista
    }

    // Atualiza a quilometragem de uma moto.
    override fun atualizarQuilometragem(id: Int, novaQuilometragem: Int) {

        // Cria uma nova lista baseada na lista atual.
        val novaLista = _motos.value.map { moto ->

            // Verifica se a moto atual é a moto que deve ser atualizada.
            if (moto.id == id) {

                // Cria uma cópia da moto alterando apenas a quilometragem.
                moto.copy(quilometragem = novaQuilometragem)
            } else {

                // Mantém as outras motos sem alteração.
                moto
            }
        }

        // Atualiza o StateFlow com a nova lista.
        _motos.value = novaLista
    }
}