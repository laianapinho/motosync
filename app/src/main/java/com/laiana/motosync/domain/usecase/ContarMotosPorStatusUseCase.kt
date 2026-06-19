package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.repository.MotoRepository

// Caso de uso responsável por contar motos por status.
class ContarMotosPorStatusUseCase(
    private val repository: MotoRepository
) {

    // Executa a contagem de motos com base no status recebido.
    suspend operator fun invoke(status: String): Int {

        // Delega a contagem para o repository.
        return repository.contarMotosPorStatus(status)
    }
}