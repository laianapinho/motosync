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

    // Adiciona uma moto fake na lista.
    fun adicionarMotoFake() {

        // Pega a lista atual de motos.
        val listaAtual = motos.value

        // Calcula o próximo id com base no maior id existente.
        val novoId = if (listaAtual.isEmpty()) {

            // Se a lista estiver vazia, o primeiro id será 1.
            1
        } else {

            // Se a lista não estiver vazia, pega o maior id e soma 1.
            listaAtual.maxOf { moto -> moto.id } + 1
        }


            // 3. Criar nomeMoto com if
            val nomeMoto = if (novoId % 2 == 0) {
                "Honda CG 160"
            } else {
                "Yamaha Fazer 250"
            }

            // 4. Criar modeloMoto com if
            val modeloMoto = if (novoId % 2 == 0) {
                "Street"
            } else {
                "Urbana"
            }

            // 5. Criar placaMoto usando novoId
            val placaMoto = "MTS-$novoId"

            // 6. Criar novaMoto usando essas variáveis
            // Cria uma nova moto fake.
        val novaMoto = Moto(
            id = novoId,
            nome = nomeMoto,
            modelo = modeloMoto,
            placa = placaMoto,
            status = "Disponível",
            ano = 2025,
            quilometragem = 0
        )

            // 7. Mandar repository.adicionarMoto(novaMoto)
             repository.adicionarMoto(novaMoto)
    }

    // Remove uma moto pelo id.
    fun removerMoto(id: Int) {

        // Pede para o repository remover a moto.
        repository.removerMoto(id)
    }

    // Aumenta a quilometragem da moto em 1000 km.
    fun aumentarQuilometragem(id: Int) {

        // Busca a moto atual pelo id.
        val motoAtual = buscarMotoPorId(id)

        // Verifica se a moto foi encontrada.
        if (motoAtual != null) {

            // Calcula a nova quilometragem.
            val novaQuilometragem = motoAtual.quilometragem + 1000

            // Pede para o repository atualizar a quilometragem.
            repository.atualizarQuilometragem(
                id = id,
                novaQuilometragem = novaQuilometragem
            )
        }
    }
}