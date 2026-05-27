package com.laiana.motosync.domain.usecase

import com.laiana.motosync.domain.model.Moto

// Caso de uso responsável por gerar uma moto fake.
class GerarMotoFakeUseCase {

    // Executa o caso de uso.
    // Recebe a lista atual de motos e retorna uma nova moto pronta.
    operator fun invoke(listaAtual: List<Moto>): Moto {

        // Calcula o próximo id com base na lista recebida.
        val novoId = if (listaAtual.isEmpty()) {
            1
        } else {
            listaAtual.maxOf { moto -> moto.id } + 1
        }

        // Define o nome da moto com base no id.
        val nomeMoto = if (novoId % 2 == 0) {
            "Honda CG 160"
        } else {
            "Yamaha Fazer 250"
        }

        // Define o modelo da moto com base no id.
        val modeloMoto = if (novoId % 2 == 0) {
            "Street"
        } else {
            "Urbana"
        }

        // Define a placa usando o id da nova moto.
        val placaMoto = "MTS-$novoId"

        // Cria a nova moto.
        val novaMoto = Moto(
            id = novoId,
            nome = nomeMoto,
            modelo = modeloMoto,
            placa = placaMoto,
            status = "Disponível",
            ano = 2024,
            quilometragem = 0
        )

        // Retorna a nova moto criada.
        return novaMoto
    }
}