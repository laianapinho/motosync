package com.laiana.motosync.data.remote.dto

// Representa exatamente o formato de dados que a API (JSONPlaceholder) devolve.
// Os nomes dos campos precisam bater com as chaves do JSON.
data class MotoDto(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)