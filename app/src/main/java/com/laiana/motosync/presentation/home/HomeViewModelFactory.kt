package com.laiana.motosync.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laiana.motosync.data.repository.RoomMotoRepository

// Factory responsável por criar o HomeViewModel com dependências.
class HomeViewModelFactory(
    private val repository: RoomMotoRepository
) : ViewModelProvider.Factory {

    // Função chamada pelo Android para criar o ViewModel.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Verifica se o ViewModel solicitado é o HomeViewModel.
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {

            // Cria o HomeViewModel passando o repository.
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }

        // Se for outro ViewModel, lança erro.
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}