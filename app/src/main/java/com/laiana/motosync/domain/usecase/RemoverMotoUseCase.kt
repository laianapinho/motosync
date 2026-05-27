package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.repository.MotoRepository

// Caso de uso responsável por remover uma moto.
class RemoverMotoUseCase(
    private val repository: MotoRepository
) {

    // Executa a remoção da moto pelo id.
    operator fun invoke(id: Int) {

        // Delega a remoção para o repository.
        repository.removerMoto(id)
    }
}