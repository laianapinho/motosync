package com.laiana.motosync.di

import android.content.Context
import com.laiana.motosync.data.local.dao.MotoDao
import com.laiana.motosync.data.local.database.MotoDatabase
import com.laiana.motosync.data.repository.RoomMotoRepository
import com.laiana.motosync.domain.repository.MotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Módulo Hilt responsável por ensinar como criar as dependências de dados.
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // Ensina o Hilt a criar o banco de dados Room.
    @Provides
    @Singleton
    fun provideMotoDatabase(@ApplicationContext context: Context): MotoDatabase {
        return MotoDatabase.getDatabase(context)
    }

    // Ensina o Hilt a pegar o DAO a partir do banco.
    @Provides
    fun provideMotoDao(database: MotoDatabase): MotoDao {
        return database.motoDao()
    }

    // Ensina o Hilt a criar o repository, entregando-o como a interface MotoRepository.
    @Provides
    @Singleton
    fun provideMotoRepository(motoDao: MotoDao): MotoRepository {
        return RoomMotoRepository(motoDao)
    }
}