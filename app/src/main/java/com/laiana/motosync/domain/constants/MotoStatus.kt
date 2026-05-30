package com.laiana.motosync.domain.constants

// Objeto responsável por guardar os status possíveis de uma moto.
object MotoStatus {

    // Status usado quando a moto está disponível para uso ou locação.
    const val DISPONIVEL = "Disponível"

    // Status usado quando a moto está alugada.
    const val ALUGADA = "Alugada"

    // Status usado quando a moto está em manutenção.
    const val MANUTENCAO = "Manutenção"
}