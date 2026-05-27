package com.laiana.motosync.data.repository

import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
            status = "Disponível",
            ano = 2022,
            quilometragem = 12500
        ),

        // Segunda moto da lista.
        Moto(
            id = 2,
            nome = "Honda Pop 110i",
            modelo = "Econômica",
            placa = "DEF-5678",
            status = "Alugada",
            ano = 2021,
            quilometragem = 18000
        ),

        // Terceira moto da lista.
        Moto(
            id = 3,
            nome = "Yamaha Factor 150",
            modelo = "Street",
            placa = "GHI-9012",
            status = "Manutenção",
            ano = 2020,
            quilometragem = 25000
        ),

        // Quarta moto da lista.
        Moto(
            id = 4,
            nome = "Honda CG 160",
            modelo = "Street",
            placa = "JKL-3456",
            status = "Disponível",
            ano = 2023,
            quilometragem = 8000
        ),

        // Quinta moto da lista.
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

                // Define o novo status com base no status atual.
                val novoStatus = when (moto.status) {

                    // Se estiver disponível, passa para alugada.
                    "Disponível" -> "Alugada"

                    // Se estiver alugada, passa para manutenção.
                    "Alugada" -> "Manutenção"

                    // Se estiver em manutenção, volta para disponível.
                    "Manutenção" -> "Disponível"

                    // Caso apareça outro status, mantém o mesmo.
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
}