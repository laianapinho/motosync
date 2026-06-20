package com.laiana.motosync.data.repository

import com.laiana.motosync.domain.model.Moto
import com.laiana.motosync.domain.repository.MotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.laiana.motosync.domain.constants.MotoStatus

// Repositório falso usado nos testes, no lugar do RoomMotoRepository.
class FakeMotoRepository : MotoRepository {

    // Lista inicial de motos usada para simular dados reais.
    private val listaInicialDeMotos = listOf(
        Moto(id = 1, nome = "Honda Biz 125", modelo = "Urbana", placa = "ABC-1234", status = MotoStatus.DISPONIVEL, ano = 2022, quilometragem = 12500),
        Moto(id = 2, nome = "Honda Pop 110i", modelo = "Econômica", placa = "DEF-5678", status = MotoStatus.ALUGADA, ano = 2021, quilometragem = 18000),
        Moto(id = 3, nome = "Yamaha Factor 150", modelo = "Street", placa = "GHI-9012", status = MotoStatus.MANUTENCAO, ano = 2020, quilometragem = 25000),
        Moto(id = 4, nome = "Honda CG 160", modelo = "Street", placa = "JKL-3456", status = MotoStatus.DISPONIVEL, ano = 2023, quilometragem = 8000),
        Moto(id = 5, nome = "Yamaha Fazer 250", modelo = "Street", placa = "MNO-7890", status = MotoStatus.DISPONIVEL, ano = 2022, quilometragem = 15000)
    )

    // StateFlow privado e mutável. Só o repositório pode alterar essa lista.
    private val _motos = MutableStateFlow(listaInicialDeMotos)

    // Observa todas as motos (mesma assinatura do RoomMotoRepository)
    override fun observarMotos(): Flow<List<Moto>> = _motos.asStateFlow()

    // Busca uma moto pelo id recebido.
    override suspend fun buscarMotoPorId(id: Int): Moto? {
        return _motos.value.find { moto -> moto.id == id }
    }

    // Altera o status de uma moto.
    override suspend fun alterarStatusDaMoto(id: Int) {
        val novaLista = _motos.value.map { moto ->
            if (moto.id == id) {
                val novoStatus = when (moto.status) {
                    MotoStatus.DISPONIVEL -> MotoStatus.ALUGADA
                    MotoStatus.ALUGADA -> MotoStatus.MANUTENCAO
                    MotoStatus.MANUTENCAO -> MotoStatus.DISPONIVEL
                    else -> moto.status
                }
                moto.copy(status = novoStatus)
            } else {
                moto
            }
        }
        _motos.value = novaLista
    }

    // Conta motos com base no status recebido.
    override suspend fun contarMotosPorStatus(status: String): Int {
        return _motos.value.count { moto -> moto.status == status }
    }

    // Adiciona uma nova moto na lista atual.
    override suspend fun adicionarMoto(moto: Moto) {
        _motos.value = _motos.value + moto
    }

    // Adiciona várias motos de uma vez.
    override suspend fun adicionarMotos(motos: List<Moto>) {
        _motos.value = _motos.value + motos
    }

    // Remove uma moto da lista usando o id recebido.
    override suspend fun removerMoto(id: Int) {
        _motos.value = _motos.value.filter { moto -> moto.id != id }
    }

    // Remove todas as motos.
    override suspend fun removerTodasAsMotos() {
        _motos.value = emptyList()
    }

    // Atualiza a quilometragem de uma moto.
    override suspend fun atualizarQuilometragem(id: Int, novaQuilometragem: Int) {
        _motos.value = _motos.value.map { moto ->
            if (moto.id == id) {
                moto.copy(quilometragem = novaQuilometragem)
            } else {
                moto
            }
        }
    }

    // Zera a quilometragem de uma moto.
    override suspend fun resetarQuilometragemMoto(id: Int) {
        _motos.value = _motos.value.map { moto ->
            if (moto.id == id) {
                moto.copy(quilometragem = 0)
            } else {
                moto
            }
        }
    }

    // Verifica se a "base" está vazia.
    override suspend fun bancoVazio(): Boolean = _motos.value.isEmpty()
}