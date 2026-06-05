# MotoSync

Aplicativo Android para gestão de motos/frota, desenvolvido com foco em boas práticas de arquitetura mobile. O app permite listar motos, visualizar detalhes, adicionar, remover, alterar status e atualizar quilometragem usando persistência local com Room Database e gerenciamento reativo de estado.

## Funcionalidades

- Listar todas as motos cadastradas.
- Visualizar detalhes de cada moto (nome, modelo, placa, status, ano, quilometragem).
- Adicionar motos fake de teste.
- Alterar status da moto (Disponível → Alugada → Manutenção → Disponível).
- Aumentar quilometragem de motos.
- Remover motos individualmente.
- Remover todas as motos de uma vez.
- Persistência local com Room Database.
- UI reativa usando Jetpack Compose.

## Tecnologias

- Kotlin
- Jetpack Compose
- MVVM
- Navigation Compose
- Room Database
- Coroutines
- Flow / StateFlow
- Repository Pattern
- UseCases

## Estrutura do projeto

```
MotoSync/
│
├─ data/
│  ├─ local/
│  │  ├─ dao/          # Contém MotoDao.kt
│  │  ├─ database/     # Contém MotoDatabase.kt
│  │  └─ entity/       # Contém MotoEntity.kt
│  └─ repository/      # Contém RoomMotoRepository.kt
│
├─ domain/
│  ├─ constants/       # Contém MotoStatus.kt
│  ├─ model/           # Contém Moto.kt
│  └─ usecase/         # Contém os usecases do app
│
├─ navigation/         # Contém MotoSyncApp.kt e Routes.kt
├─ presentation/
│  ├─ home/            # HomeScreen, HomeViewModel, HomeViewModelFactory
│  └─ details/         # Tela de detalhes das motos
├─ build.gradle.kts
└─ settings.gradle.kts
```

## Como rodar

1. Abra o projeto no Android Studio (versão compatível com Kotlin 2.0+).
2. Faça `Sync` das dependências.
3. Conecte um emulador ou dispositivo Android.
4. Execute o app.

## Observações

- O banco de dados é populado com motos iniciais apenas se estiver vazio, evitando duplicação.
- As mudanças no banco (adicionar, remover, alterar status) são observadas automaticamente pela UI usando Flow/StateFlow.
- Os botões de ação (Adicionar, Remover, Remover todas, Alterar status, Aumentar quilometragem) chamam funções do ViewModel que executam coroutines no `viewModelScope`.
