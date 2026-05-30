package com.laiana.motosync.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.laiana.motosync.data.local.dao.MotoDao
import com.laiana.motosync.data.local.entity.MotoEntity

// Define o banco de dados local do aplicativo.
@Database(
    entities = [MotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MotoDatabase : RoomDatabase() {

    // Disponibiliza o DAO de motos para acessar a tabela motos.
    abstract fun motoDao(): MotoDao
}