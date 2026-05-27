package com.laiana.motosync.presentation.home

import androidx.lifecycle.ViewModel
import com.laiana.motosync.data.repository.FakeMotoRepository
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository
import kotlinx.coroutines.flow.StateFlow

// ViewModel da tela inicial.
class HomeViewModel : ViewModel() {

    // Cria o repositório de motos.
    // Por enquanto usamos FakeMotoRepository.
    // No futuro, poderemos trocar por Room, API ou Firebase.
    private val repository: MotoRepository = FakeMotoRepository()

    // Expõe a lista de motos para a tela.
    // A lista vem do repositório.
    val motos: StateFlow<List<Moto>> = repository.motos

    // Busca uma moto pelo id usando o repositório.
    fun buscarMotoPorId(id: Int?): Moto? {

        // Delega a busca para o repository.
        return repository.buscarMotoPorId(id)
    }

    // Altera o status da moto usando o repositório.
    fun alterarStatusDaMoto(id: Int) {

        // Delega a alteração para o repository.
        repository.alterarStatusDaMoto(id)
    }

    // Conta quantas motos estão disponíveis.
    fun contarMotosDisponiveis(): Int {

        // Delega a alteração para o repository.
        return repository.contarMotosPorStatus("Disponível")
    }

    // Conta quantas motos estão alugadas.
    fun contarMotosAlugadas(): Int {

        return repository.contarMotosPorStatus("Alugada")
    }

    // Conta quantas motos estão em manutenção.
    fun contarMotosEmManutencao(): Int {

        // Usa o repository para contar motos em manutenção.
        return repository.contarMotosPorStatus("Manutenção")
    }
}