package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.repository.MotoRepository

// Caso de uso responsável por alterar o status de uma moto.
class AlterarStatusDaMotoUseCase(
    private val repository: MotoRepository
) {

    // Executa a alteração de status.
    suspend operator fun invoke(id: Int) {

        // Delega a alteração para o repository.
        repository.alterarStatusDaMoto(id)
    }
}