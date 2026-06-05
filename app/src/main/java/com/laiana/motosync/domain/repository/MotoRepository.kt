package com.laiana.motosync.domain.repository

import com.laiana.motosync.domain.model.Moto
import kotlinx.coroutines.flow.StateFlow

// Interface que define o contrato do repositório de motos.
interface MotoRepository {

    // Lista observável de motos.
    val motos: StateFlow<List<Moto>>

    // Função responsável por buscar uma moto pelo id.
    fun buscarMotoPorId(id: Int?): Moto?

    // Função responsável por alterar o status de uma moto.
    fun alterarStatusDaMoto(id: Int)

    // Função responsável por contar motos com base no status informado.
    fun contarMotosPorStatus(status: String): Int

    // Função responsável por adicionar uma nova moto.
    fun adicionarMoto(moto: Moto)

    // Função responsável por remover uma moto pelo id.
    fun removerMoto(id: Int)

    // Função responsável por atualizar a quilometragem de uma moto.
    fun atualizarQuilometragem(id: Int, novaQuilometragem: Int)

}