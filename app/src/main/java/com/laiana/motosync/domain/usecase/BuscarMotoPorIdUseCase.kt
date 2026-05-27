package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository

// Caso de uso responsável por buscar uma moto pelo id.
class BuscarMotoPorIdUseCase(
    private val repository: MotoRepository
) {

    // Executa a busca da moto.
    operator fun invoke(id: Int?): Moto? {

        // Delega a busca para o repository.
        return repository.buscarMotoPorId(id)
    }
}