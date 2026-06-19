package com.laiana.motosync.data.repository

import com.laiana.motosync.data.local.dao.MotoDao
import com.laiana.motosync.data.mapper.toDomain
import com.laiana.motosync.data.mapper.toEntity
import com.laiana.motosync.domain.constants.MotoStatus
import com.laiana.motosync.domain.model.Moto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.laiana.motosync.domain.repository.MotoRepository

// Repository responsável por acessar as motos salvas no Room.
// Ele usa MotoDao para conversar com o banco local.
class RoomMotoRepository(
    private val motoDao: MotoDao
) : MotoRepository {

    // Observa todas as motos salvas no banco.
    override fun observarMotos(): Flow<List<Moto>> {
        return motoDao.observarMotos().map { listaEntity ->
            listaEntity.map { motoEntity ->
                motoEntity.toDomain()
            }
        }
    }

    // Busca uma moto pelo id.
    override suspend fun buscarMotoPorId(id: Int): Moto? {
        val motoEntity = motoDao.buscarMotoPorId(id)
        return motoEntity?.toDomain()
    }

    // Adiciona uma moto no banco local.
    override suspend fun adicionarMoto(moto: Moto) {
        motoDao.inserirMoto(moto.toEntity())
    }

    // Remove uma moto pelo id.
    override suspend fun removerMoto(id: Int) {
        motoDao.removerMotoPorId(id)
    }

    // Atualiza a quilometragem de uma moto.
    override suspend fun atualizarQuilometragem(
        id: Int,
        novaQuilometragem: Int
    ) {
        val motoAtual = motoDao.buscarMotoPorId(id)
        if (motoAtual != null) {
            val motoAtualizada = motoAtual.copy(
                quilometragem = novaQuilometragem
            )
            motoDao.atualizarMoto(motoAtualizada)
        }
    }

    // Altera o status de uma moto.
    override suspend fun alterarStatusDaMoto(id: Int) {
        val motoAtual = motoDao.buscarMotoPorId(id)
        if (motoAtual != null) {
            val novoStatus = when (motoAtual.status) {
                MotoStatus.DISPONIVEL -> MotoStatus.ALUGADA
                MotoStatus.ALUGADA -> MotoStatus.MANUTENCAO
                MotoStatus.MANUTENCAO -> MotoStatus.DISPONIVEL
                else -> motoAtual.status
            }
            val motoAtualizada = motoAtual.copy(
                status = novoStatus
            )
            motoDao.atualizarMoto(motoAtualizada)
        }
    }

    // Conta quantas motos existem com determinado status.
    override suspend fun contarMotosPorStatus(status: String): Int {
        return motoDao.contarPorStatus(status)
    }

    override suspend fun adicionarMotos(motos: List<Moto>) {
        motos.forEach { moto ->
            motoDao.inserirMoto(moto.toEntity())
        }
    }

    // Remove todas as motos do banco local.
    override suspend fun removerTodasAsMotos() {
        motoDao.removerTodasAsMotos()
    }

    // verifica se o banco está vazio
    override suspend fun bancoVazio(): Boolean = motoDao.contarTodasMotos() == 0

    //zera a quilometragem da moto
    override suspend fun resetarQuilometragemMoto(id: Int) {
        val motoAtual = motoDao.buscarMotoPorId(id)
        if (motoAtual != null) {
            val motoAtualizada = motoAtual.copy(
                quilometragem = 0
            )
            motoDao.atualizarMoto(motoAtualizada)
        }
    }
}