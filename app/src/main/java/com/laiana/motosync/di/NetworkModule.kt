package com.laiana.motosync.di

import com.laiana.motosync.data.remote.api.MotoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Módulo Hilt responsável por ensinar como criar as dependências de rede.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // URL base da API. Toda chamada do MotoApiService é somada a essa URL.
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // Ensina o Hilt a criar um interceptor que loga as requisições no Logcat.
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Ensina o Hilt a criar o cliente HTTP (OkHttp), usando o interceptor de log.
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // Ensina o Hilt a criar o Retrofit, usando a URL base e o cliente HTTP.
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Ensina o Hilt a criar o MotoApiService a partir do Retrofit.
    @Provides
    @Singleton
    fun provideMotoApiService(retrofit: Retrofit): MotoApiService {
        return retrofit.create(MotoApiService::class.java)
    }
}