package com.laiana.motosync.data.mapper

import com.laiana.motosync.data.remote.dto.MotoDto
import com.laiana.motosync.domain.constants.MotoStatus
import com.laiana.motosync.domain.model.Moto

// Converte um MotoDto (vindo da API) para Moto (modelo usado no app).
fun MotoDto.toDomain(): Moto {
    return Moto(
        id = this.id,
        nome = this.name,
        modelo = this.username,
        placa = this.email,
        status = MotoStatus.DISPONIVEL, // a API não tem esse campo, usamos um valor padrão
        ano = 2024,                     // a API não tem esse campo, usamos um valor padrão
        quilometragem = 0                // a API não tem esse campo, usamos um valor padrão
    )
}