package com.laiana.motosync.data.repository

import com.laiana.motosync.data.local.dao.MotoDao
import com.laiana.motosync.data.mapper.toDomain
import com.laiana.motosync.data.mapper.toEntity
import com.laiana.motosync.domain.constants.MotoStatus
import com.laiana.motosync.domain.model.Moto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Repository responsável por acessar as motos salvas no Room.
// Ele usa MotoDao para conversar com o banco local.
class RoomMotoRepository(
    private val motoDao: MotoDao
) {

    // Observa todas as motos salvas no banco.
    // Retorna Flow porque o Room pode avisar quando os dados mudarem.
    fun observarMotos(): Flow<List<Moto>> {

        // Chama o DAO para observar as motos como MotoEntity.
        return motoDao.observarMotos().map { listaEntity ->

            // Converte cada MotoEntity da lista para Moto.
            listaEntity.map { motoEntity ->

                // Usa o mapper para transformar entidade em domínio.
                motoEntity.toDomain()
            }
        }
    }

    // Busca uma moto pelo id.
    suspend fun buscarMotoPorId(id: Int): Moto? {

        // Busca a moto no banco como MotoEntity.
        val motoEntity = motoDao.buscarMotoPorId(id)

        // Se encontrou a entidade, converte para Moto.
        return motoEntity?.toDomain()
    }

    // Adiciona uma moto no banco local.
    suspend fun adicionarMoto(moto: Moto) {

        // Converte Moto para MotoEntity e manda o DAO inserir.
        motoDao.inserirMoto(moto.toEntity())
    }

    // Remove uma moto pelo id.
    suspend fun removerMoto(id: Int) {

        // Pede ao DAO para remover a moto pelo id.
        motoDao.removerMotoPorId(id)
    }

    // Atualiza a quilometragem de uma moto.
    suspend fun atualizarQuilometragem(
        id: Int,
        novaQuilometragem: Int
    ) {

        // Busca a moto atual no banco.
        val motoAtual = motoDao.buscarMotoPorId(id)

        // Verifica se a moto existe.
        if (motoAtual != null) {

            // Cria uma cópia da moto alterando apenas a quilometragem.
            val motoAtualizada = motoAtual.copy(
                quilometragem = novaQuilometragem
            )

            // Atualiza a moto no banco.
            motoDao.atualizarMoto(motoAtualizada)
        }
    }

    // Altera o status de uma moto.
    suspend fun alterarStatusDaMoto(id: Int) {

        // Busca a moto atual no banco.
        val motoAtual = motoDao.buscarMotoPorId(id)

        // Verifica se encontrou a moto.
        if (motoAtual != null) {

            // Define o novo status com base no status atual.
            val novoStatus = when (motoAtual.status) {

                // Se estiver disponível, passa para alugada.
                MotoStatus.DISPONIVEL -> MotoStatus.ALUGADA

                // Se estiver alugada, passa para manutenção.
                MotoStatus.ALUGADA -> MotoStatus.MANUTENCAO

                // Se estiver em manutenção, volta para disponível.
                MotoStatus.MANUTENCAO -> MotoStatus.DISPONIVEL

                // Se for outro status, mantém igual.
                else -> motoAtual.status
            }

            // Cria uma cópia alterando apenas o status.
            val motoAtualizada = motoAtual.copy(
                status = novoStatus
            )

            // Atualiza a moto no banco.
            motoDao.atualizarMoto(motoAtualizada)
        }
    }

    // Conta quantas motos existem com determinado status.
    suspend fun contarMotosPorStatus(status: String): Int {

        // Pede ao DAO para contar as motos com o status informado.
        return motoDao.contarPorStatus(status)
    }

    suspend fun adicionarMotos(motos: List<Moto>) {
        // percorra a lista Para cada moto dentro da lista motos...
        motos.forEach { moto ->

            //Converta essa moto para o formato do banco e salve.
            motoDao.inserirMoto(moto.toEntity())
        }
    }

    // Remove todas as motos do banco local.
    //não recebe nenhum parâmetro, porque vai apagar tudo.
    suspend fun removerTodasAsMotos() {

        // Pede ao DAO para apagar todos os registros da tabela motos.
        motoDao.removerTodasAsMotos()
    }

    // verifica se o banco está vazio
    suspend fun bancoVazio(): Boolean = motoDao.contarTodasMotos() == 0
}