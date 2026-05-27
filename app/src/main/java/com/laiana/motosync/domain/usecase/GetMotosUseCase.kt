package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository
import kotlinx.coroutines.flow.StateFlow

// Caso de uso responsável por buscar a lista de motos.
class GetMotosUseCase(
    private val repository: MotoRepository
) {

    // Executa o caso de uso.
    // Retorna a lista observável de motos que vem do repository.
    operator fun invoke(): StateFlow<List<Moto>> {

        // Devolve a lista de motos do repository.
        return repository.motos
    }
}