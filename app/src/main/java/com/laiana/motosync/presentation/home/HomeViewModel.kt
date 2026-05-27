package com.laiana.motosync.presentation.home

import androidx.lifecycle.ViewModel
import com.laiana.motosync.data.repository.FakeMotoRepository
import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository
import com.laiana.motosync.domain.usecase.AdicionarMotoUseCase
import com.laiana.motosync.domain.usecase.AlterarStatusDaMotoUseCase
import com.laiana.motosync.domain.usecase.AtualizarQuilometragemUseCase
import com.laiana.motosync.domain.usecase.BuscarMotoPorIdUseCase
import com.laiana.motosync.domain.usecase.ContarMotosPorStatusUseCase
import com.laiana.motosync.domain.usecase.GetMotosUseCase
import com.laiana.motosync.domain.usecase.RemoverMotoUseCase
import com.laiana.motosync.domain.usecase.GerarMotoFakeUseCase
import kotlinx.coroutines.flow.StateFlow

// ViewModel da tela inicial.
class HomeViewModel : ViewModel() {

    // Cria o repository usado pelo ViewModel.
    private val repository: MotoRepository = FakeMotoRepository()

    // Cria o caso de uso responsável por buscar a lista de motos.
    private val getMotosUseCase = GetMotosUseCase(repository)

    // Cria o caso de uso responsável por buscar uma moto pelo id.
    private val buscarMotoPorIdUseCase = BuscarMotoPorIdUseCase(repository)

    // Cria o caso de uso responsável por alterar o status de uma moto.
    private val alterarStatusDaMotoUseCase = AlterarStatusDaMotoUseCase(repository)

    // Cria o caso de uso responsável por adicionar uma moto.
    private val adicionarMotoUseCase = AdicionarMotoUseCase(repository)

    // Cria o caso de uso responsável por remover uma moto.
    private val removerMotoUseCase = RemoverMotoUseCase(repository)

    // Cria o caso de uso responsável por atualizar quilometragem.
    private val atualizarQuilometragemUseCase = AtualizarQuilometragemUseCase(repository)

    // Cria o caso de uso responsável por contar motos por status.
    private val contarMotosPorStatusUseCase = ContarMotosPorStatusUseCase(repository)

    // Cria o caso de uso responsável por gerar uma moto fake.
    private val gerarMotoFakeUseCase = GerarMotoFakeUseCase()

    // Expõe a lista de motos para a tela.
    val motos: StateFlow<List<Moto>> = getMotosUseCase()

    // Busca uma moto pelo id.
    fun buscarMotoPorId(id: Int?): Moto? {

        // Executa o caso de uso de buscar moto por id.
        return buscarMotoPorIdUseCase(id)
    }

    // Altera o status de uma moto.
    fun alterarStatusDaMoto(id: Int) {

        // Executa o caso de uso de alterar status.
        alterarStatusDaMotoUseCase(id)
    }

    // Conta quantas motos estão disponíveis.
    fun contarMotosDisponiveis(): Int {

        // Executa o caso de uso de contagem usando o status Disponível.
        return contarMotosPorStatusUseCase("Disponível")
    }

    // Conta quantas motos estão alugadas.
    fun contarMotosAlugadas(): Int {

        // Executa o caso de uso de contagem usando o status Alugada.
        return contarMotosPorStatusUseCase("Alugada")
    }

    // Conta quantas motos estão em manutenção.
    fun contarMotosEmManutencao(): Int {

        // Executa o caso de uso de contagem usando o status Manutenção.
        return contarMotosPorStatusUseCase("Manutenção")
    }

    // Adiciona uma moto fake na lista.
    fun adicionarMotoFake() {

        // Gera uma nova moto fake usando a lista atual.
        val novaMoto = gerarMotoFakeUseCase(motos.value)

        // Adiciona a nova moto usando o caso de uso de adicionar.
        adicionarMotoUseCase(novaMoto)
    }

    // Remove uma moto pelo id.
    fun removerMoto(id: Int) {

        // Executa o caso de uso de remover moto.
        removerMotoUseCase(id)
    }

    // Aumenta a quilometragem de uma moto.
    fun aumentarQuilometragem(id: Int) {

        // Busca a moto atual pelo id.
        val motoAtual = buscarMotoPorId(id)

        // Verifica se a moto foi encontrada.
        if (motoAtual != null) {

            // Calcula a nova quilometragem.
            val novaQuilometragem = motoAtual.quilometragem + 1000

            // Executa o caso de uso de atualizar quilometragem.
            atualizarQuilometragemUseCase(
                id = id,
                novaQuilometragem = novaQuilometragem
            )
        }
    }
}