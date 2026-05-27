package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.repository.MotoRepository

// Caso de uso responsável por atualizar a quilometragem de uma moto.
class AtualizarQuilometragemUseCase(
    private val repository: MotoRepository
) {

    // Executa a atualização da quilometragem.
    operator fun invoke(id: Int, novaQuilometragem: Int) {

        // Delega a atualização para o repository.
        repository.atualizarQuilometragem(
            id = id,
            novaQuilometragem = novaQuilometragem
        )
    }
}