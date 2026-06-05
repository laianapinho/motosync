package com.laiana.motosync.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.laiana.motosync.data.local.entity.MotoEntity
import kotlinx.coroutines.flow.Flow

// Define as operações que podem ser feitas na tabela de motos.
@Dao
interface MotoDao {

    // Busca todas as motos salvas no banco local.
    // O Flow permite observar mudanças na tabela automaticamente.
    @Query("SELECT * FROM motos")
    fun observarMotos(): Flow<List<MotoEntity>>

    // Busca uma moto específica pelo id.
    @Query("SELECT * FROM motos WHERE id = :id LIMIT 1")
    suspend fun buscarMotoPorId(id: Int): MotoEntity?

    // Insere uma moto no banco.
    // Se já existir outra moto com o mesmo id, ela será substituída.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirMoto(moto: MotoEntity)

    // Atualiza uma moto existente.
    @Update
    suspend fun atualizarMoto(moto: MotoEntity)

    // Remove uma moto do banco.
    @Delete
    suspend fun removerMoto(moto: MotoEntity)

    // Remove uma moto diretamente pelo id.
    @Query("DELETE FROM motos WHERE id = :id")
    suspend fun removerMotoPorId(id: Int)

    // Conta quantas motos existem com determinado status.
    @Query("SELECT COUNT(*) FROM motos WHERE status = :status")
    suspend fun contarPorStatus(status: String): Int

    //Busca moto por status
    // Busca uma moto específica pelo id.
    @Query("SELECT * FROM motos WHERE status = :status")
    fun observarMotosPorStatus(status: String): Flow<List<MotoEntity>>

    // Remove todas as motos da tabela motos.
    @Query("DELETE FROM motos")
    suspend fun removerTodasAsMotos()

    // Nova função para contar todas as motos, usada para saber se o banco está vazio
    @Query("SELECT COUNT(*) FROM motos")
    suspend fun contarTodasMotos(): Int
}