package com.laiana.motosync.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Representa a tabela de motos dentro do banco de dados local.
@Entity(tableName = "motos")
data class MotoEntity(

    // Define o id como chave primária da tabela.
    @PrimaryKey
    val id: Int,

    // Coluna que guarda o nome da moto.
    val nome: String,

    // Coluna que guarda o modelo da moto.
    val modelo: String,

    // Coluna que guarda a placa da moto.
    val placa: String,

    // Coluna que guarda o status da moto.
    val status: String,

    // Coluna que guarda o ano da moto.
    val ano: Int,

    // Coluna que guarda a quilometragem da moto.
    val quilometragem: Int
)