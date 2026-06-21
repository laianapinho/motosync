package com.laiana.motosync.data.remote.api

import com.laiana.motosync.data.remote.dto.MotoDto
import retrofit2.http.GET

// Interface que descreve os endpoints disponíveis na API.
// O Retrofit gera a implementação real dela automaticamente.
interface MotoApiService {

    // Busca a lista de "motos" (na prática, usuários do JSONPlaceholder).
    @GET("users")
    suspend fun listarMotos(): List<MotoDto>
}