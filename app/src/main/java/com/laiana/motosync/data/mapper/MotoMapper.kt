package com.laiana.motosync.data.mapper

import com.laiana.motosync.data.local.entity.MotoEntity
import com.laiana.motosync.domain.model.Moto

// Converte uma MotoEntity em uma Moto.
// MotoEntity é o formato usado pelo banco Room.
// Moto é o formato usado pelo restante do aplicativo.
fun MotoEntity.toDomain(): Moto {

    // Cria e retorna uma Moto usando os dados da MotoEntity.
    return Moto(

        // Copia o id da entidade para o modelo de domínio.
        id = id,

        // Copia o nome da entidade para o modelo de domínio.
        nome = nome,

        // Copia o modelo da entidade para o modelo de domínio.
        modelo = modelo,

        // Copia a placa da entidade para o modelo de domínio.
        placa = placa,

        // Copia o status da entidade para o modelo de domínio.
        status = status,

        // Copia o ano da entidade para o modelo de domínio.
        ano = ano,

        // Copia a quilometragem da entidade para o modelo de domínio.
        quilometragem = quilometragem
    )
}

// Converte uma Moto em uma MotoEntity.
// Moto é o formato usado pelo app.
// MotoEntity é o formato usado pelo banco Room.
fun Moto.toEntity(): MotoEntity {

    // Cria e retorna uma MotoEntity usando os dados da Moto.
    return MotoEntity(

        // Copia o id da Moto para a entidade.
        id = id,

        // Copia o nome da Moto para a entidade.
        nome = nome,

        // Copia o modelo da Moto para a entidade.
        modelo = modelo,

        // Copia a placa da Moto para a entidade.
        placa = placa,

        // Copia o status da Moto para a entidade.
        status = status,

        // Copia o ano da Moto para a entidade.
        ano = ano,

        // Copia a quilometragem da Moto para a entidade.
        quilometragem = quilometragem
    )
}