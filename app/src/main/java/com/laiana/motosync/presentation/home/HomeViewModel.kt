package com.laiana.motosync.presentation.home

import androidx.lifecycle.ViewModel
import com.laiana.motosync.domain.model.Moto

// Cria o ViewModel da tela inicial.
// O ViewModel é responsável por guardar e controlar os dados da HomeScreen.
class HomeViewModel : ViewModel() {

    // Cria uma lista fixa de motos.
    // Por enquanto, os dados ainda são fake.
    // Mais para frente, esses dados virão de Repository, Room ou API.
    val motos = listOf(

        // Primeira moto da lista.
        Moto(
            id = 1,
            nome = "Honda Biz 125",
            modelo = "Urbana",
            placa = "ABC-1234",
            status = "Disponível",
            ano = 2022,
            quilometragem = 12500
        ),

        // Segunda moto da lista.
        Moto(
            id = 2,
            nome = "Honda Pop 110i",
            modelo = "Econômica",
            placa = "DEF-5678",
            status = "Alugada",
            ano = 2021,
            quilometragem = 18000
        ),

        // Terceira moto da lista.
        Moto(
            id = 3,
            nome = "Yamaha Factor 150",
            modelo = "Street",
            placa = "GHI-9012",
            status = "Manutenção",
            ano = 2020,
            quilometragem = 25000
        ),

        // Quarta moto da lista.
        Moto(
            id = 4,
            nome = "Honda CG 160",
            modelo = "Street",
            placa = "JKL-3456",
            status = "Disponível",
            ano = 2023,
            quilometragem = 8000
        ),

        // Quinta moto da lista.
        Moto(
            id = 5,
            nome = "Yamaha Fazer 250",
            modelo = "Street",
            placa = "MNO-7890",
            status = "Disponível",
            ano = 2022,
            quilometragem = 15000
        )
    )

    // Função usada para buscar uma moto pelo id.
    // Ela será usada na tela de detalhes.
    fun buscarMotoPorId(id: Int?): Moto? {

        // Procura dentro da lista a moto cujo id seja igual ao id recebido.
        return motos.find { moto ->

            // Compara o id da moto atual com o id recebido.
            moto.id == id
        }
    }

    fun contarMotosDisponiveis(): Int{
        // Percorre a lista de motos e conta apenas as motos com status "Disponível".
        // Verifica se o status da moto atual é igual a "Disponível".
        return motos.count {moto -> moto.status == "Disponível"
        }
    }

    fun contarMotosAlugadas(): Int{
        // Percorre a lista de motos e conta apenas as motos com status "Alugada".
        // Verifica se o status da moto atual é igual a "Alugada".
        return motos.count {moto -> moto.status == "Alugada"
        }
    }

    fun contarMotosEmManutencao(): Int{
        // Percorre a lista de motos e conta apenas as motos com status "Manutenção".
        // Verifica se o status da moto atual é igual a "Manutenção".
        return motos.count {moto -> moto.status == "Manutenção"
        }
    }
}