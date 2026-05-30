package com.laiana.motosync.navigation

// Objeto responsável por guardar as rotas de navegação do aplicativo.
object Routes {

    // Rota da tela inicial.
    const val HOME = "home"

    // Rota base da tela de detalhes.
    const val DETALHES = "detalhes"

    // Nome do argumento usado para enviar o id da moto.
    const val MOTO_ID = "motoId"

    // Rota completa da tela de detalhes com argumento.
    const val DETALHES_COM_ARGUMENTO = "$DETALHES/{$MOTO_ID}"

    // Função responsável por montar a rota de detalhes com o id real da moto.
    fun detalhesComId(id: Int): String {

        // Retorna a rota no formato detalhes/1, detalhes/2, detalhes/3 etc.
        return "$DETALHES/$id"
    }
}