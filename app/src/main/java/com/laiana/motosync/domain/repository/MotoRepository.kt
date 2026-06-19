package com.laiana.motosync.domain.repository

import com.laiana.motosync.domain.model.Moto
import kotlinx.coroutines.flow.Flow

interface MotoRepository {

    fun observarMotos(): Flow<List<Moto>>

    suspend fun buscarMotoPorId(id: Int): Moto?

    suspend fun adicionarMoto(moto: Moto)

    suspend fun adicionarMotos(motos: List<Moto>)

    suspend fun removerMoto(id: Int)

    suspend fun removerTodasAsMotos()

    suspend fun atualizarQuilometragem(id: Int, novaQuilometragem: Int)

    suspend fun resetarQuilometragemMoto(id: Int)

    suspend fun alterarStatusDaMoto(id: Int)

    suspend fun contarMotosPorStatus(status: String): Int

    suspend fun bancoVazio(): Boolean
}