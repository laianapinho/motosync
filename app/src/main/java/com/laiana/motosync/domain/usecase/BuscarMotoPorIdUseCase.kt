package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository

// Caso de uso responsável por buscar uma moto pelo id.
class BuscarMotoPorIdUseCase(
    private val repository: MotoRepository
) {

    // Executa a busca da moto.
    suspend operator fun invoke(id: Int?): Moto? {

        // Se não veio id, não tem o que buscar.
        if (id == null) return null

        // Delega a busca para o repository.
        return repository.buscarMotoPorId(id)
    }
}