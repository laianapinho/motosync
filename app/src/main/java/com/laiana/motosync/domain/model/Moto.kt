package com.laiana.motosync.domain.model

data class Moto(
    val id: Int,
    val nome: String,
    val modelo: String,
    val placa: String,
    var status: String,
    val ano: Int,
    val quilometragem:Int
)