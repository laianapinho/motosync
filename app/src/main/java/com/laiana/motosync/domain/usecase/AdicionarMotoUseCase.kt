package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository

// Caso de uso responsável por adicionar uma nova moto.
class AdicionarMotoUseCase(
    private val repository: MotoRepository
) {

    // Executa a adição da moto.
    suspend operator fun invoke(moto: Moto) {

        // Delega a adição para o repository.
        repository.adicionarMoto(moto)
    }
}