package com.laiana.motosync.di

import android.content.Context
import com.laiana.motosync.data.local.dao.MotoDao
import com.laiana.motosync.data.local.database.MotoDatabase
import com.laiana.motosync.data.remote.api.MotoApiService
import com.laiana.motosync.data.repository.RemoteMotoRepository
import com.laiana.motosync.domain.repository.MotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMotoDatabase(@ApplicationContext context: Context): MotoDatabase {
        return MotoDatabase.getDatabase(context)
    }

    @Provides
    fun provideMotoDao(database: MotoDatabase): MotoDao {
        return database.motoDao()
    }

    // Agora entrega o RemoteMotoRepository, que busca da API (JSONPlaceholder).
    @Provides
    @Singleton
    fun provideMotoRepository(apiService: MotoApiService): MotoRepository {
        return RemoteMotoRepository(apiService)
    }
}