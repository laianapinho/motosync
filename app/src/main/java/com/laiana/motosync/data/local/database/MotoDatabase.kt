package com.laiana.motosync.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.laiana.motosync.data.local.dao.MotoDao
import com.laiana.motosync.data.local.entity.MotoEntity

// Define o banco de dados local do aplicativo.
@Database(
    // Informa quais tabelas fazem parte desse banco.
    entities = [MotoEntity::class],

    // Define a versão atual do banco.
    version = 1,

    // Desativa a exportação automática do esquema do banco.
    exportSchema = false
)

// Cria a classe principal do banco local.
// Ela herda de RoomDatabase porque será controlada pelo Room.
abstract class MotoDatabase : RoomDatabase() {

    // Disponibiliza o DAO de motos.
    // O DAO contém as operações feitas na tabela de motos.
    abstract fun motoDao(): MotoDao

    // Cria uma área parecida com "static" do Java.
    // Isso permite acessar getDatabase() sem criar um objeto MotoDatabase manualmente.
    companion object {

        // Guarda uma única instância do banco.
        // O @Volatile ajuda a garantir segurança quando mais de uma thread acessa essa variável.
        @Volatile
        private var INSTANCE: MotoDatabase? = null

        // Função responsável por criar ou recuperar o banco local.
        fun getDatabase(context: Context): MotoDatabase {

            // Se INSTANCE já existir, retorna essa instância.
            // Se INSTANCE for nulo, cria o banco dentro do synchronized.
            return INSTANCE ?: synchronized(this) {

                // Cria o banco usando o Room.
                val instance = Room.databaseBuilder(

                    // Usa o contexto da aplicação para evitar vazamento de memória.
                    context.applicationContext,

                    // Informa qual classe representa o banco.
                    MotoDatabase::class.java,

                    // Define o nome do arquivo do banco no dispositivo.
                    "motosync_database"
                ).build()

                // Salva a instância criada na variável INSTANCE.
                INSTANCE = instance

                // Retorna a instância criada.
                instance
            }
        }
    }
}