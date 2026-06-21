package com.laiana.motosync.data.repository

import com.laiana.motosync.data.mapper.toDomain
import com.laiana.motosync.data.remote.api.MotoApiService
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// Repository que busca as motos de uma API remota, em vez do banco local.
class RemoteMotoRepository @Inject constructor(
    private val apiService: MotoApiService
) : MotoRepository {

    // Mantém uma cópia em memória da última lista buscada,
    // já que a API não tem como "observar" mudanças como o Room faz.
    private var cache: List<Moto> = emptyList()

    override fun observarMotos(): Flow<List<Moto>> = flow {
        val motosDaApi = apiService.listarMotos().map { it.toDomain() }
        cache = motosDaApi
        emit(motosDaApi)
    }

    override suspend fun buscarMotoPorId(id: Int): Moto? {
        return cache.find { it.id == id }
    }

    override suspend fun adicionarMoto(moto: Moto) {
        // A API pública do JSONPlaceholder aceita o POST, mas não salva de verdade.
        cache = cache + moto
    }

    override suspend fun adicionarMotos(motos: List<Moto>) {
        cache = cache + motos
    }

    override suspend fun removerMoto(id: Int) {
        cache = cache.filter { it.id != id }
    }

    override suspend fun removerTodasAsMotos() {
        cache = emptyList()
    }

    override suspend fun atualizarQuilometragem(id: Int, novaQuilometragem: Int) {
        cache = cache.map {
            if (it.id == id) it.copy(quilometragem = novaQuilometragem) else it
        }
    }

    override suspend fun resetarQuilometragemMoto(id: Int) {
        cache = cache.map {
            if (it.id == id) it.copy(quilometragem = 0) else it
        }
    }

    override suspend fun alterarStatusDaMoto(id: Int) {
        cache = cache.map { moto ->
            if (moto.id == id) {
                val novoStatus = when (moto.status) {
                    com.laiana.motosync.domain.constants.MotoStatus.DISPONIVEL -> com.laiana.motosync.domain.constants.MotoStatus.ALUGADA
                    com.laiana.motosync.domain.constants.MotoStatus.ALUGADA -> com.laiana.motosync.domain.constants.MotoStatus.MANUTENCAO
                    com.laiana.motosync.domain.constants.MotoStatus.MANUTENCAO -> com.laiana.motosync.domain.constants.MotoStatus.DISPONIVEL
                    else -> moto.status
                }
                moto.copy(status = novoStatus)
            } else moto
        }
    }

    override suspend fun contarMotosPorStatus(status: String): Int {
        return cache.count { it.status == status }
    }

    override suspend fun bancoVazio(): Boolean {
        return cache.isEmpty()
    }
}