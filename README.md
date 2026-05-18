# MotoSync — Dia 1

## Visão geral

O **MotoSync** é um projeto Android desenvolvido do zero em **Kotlin** com **Jetpack Compose**. O objetivo do projeto é construir, de forma progressiva, um aplicativo moderno para gestão inteligente de motos/frota.

Este repositório faz parte de um plano de estudos prático para aprender Android moderno, arquitetura, banco local, APIs, autenticação, Firebase, testes e boas práticas profissionais.

---

## Objetivo do Dia 1

O objetivo do primeiro dia foi configurar o ambiente de desenvolvimento e criar a primeira versão funcional do aplicativo.

Ao final do Dia 1, o app deve:

- abrir no Android Studio;
- compilar sem erros;
- rodar no emulador Android;
- exibir a primeira tela com o nome do projeto;
- estar versionado com Git;
- estar conectado ao GitHub.

---

## Tecnologias usadas no Dia 1

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Gradle Kotlin DSL
- Git
- GitHub
- Android Emulator

---

## Estrutura inicial do projeto

```text
MotoSync/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/laiana/motosync/
│   │       │   ├── MainActivity.kt
│   │       │   └── ui/theme/
│   │       └── AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── README.md
```

---

## Primeira tela implementada

No Dia 1, foi criada uma tela inicial simples usando Jetpack Compose.

A tela mostra:

```text
MotoSync
Gestão inteligente de motos
```

Essa tela inicial foi criada dentro da função `HomeScreen()`.

---

## Conceitos aprendidos

### MainActivity

A `MainActivity` é a primeira tela executada quando o aplicativo Android abre.

### setContent

O `setContent` é usado para definir a interface visual do aplicativo quando usamos Jetpack Compose.

### Composable

Funções anotadas com `@Composable` são usadas para construir componentes visuais da interface.

Exemplo:

```kotlin
@Composable
fun HomeScreen() {
    Text(text = "MotoSync")
}
```

### Preview

O `@Preview` permite visualizar a tela diretamente no Android Studio, sem precisar executar o aplicativo no emulador.

---

## Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/laianapinho/motosync.git
```

### 2. Entrar na pasta do projeto

```bash
cd motosync
```

### 3. Abrir no Android Studio

Abra o Android Studio e selecione:

```text
File > Open
```

Depois escolha a pasta do projeto `motosync`.

### 4. Sincronizar o Gradle

Aguarde o Android Studio finalizar a sincronização do Gradle.

### 5. Rodar no emulador

Crie ou selecione um dispositivo virtual no **Device Manager** e clique em **Run**.

---

## Comandos Git usados no Dia 1

```bash
git init
git add .
git commit -m "chore: create MotoSync Android project"
git branch -M main
git remote add origin https://github.com/laianapinho/motosync.git
git push -u origin main
```

---

## Status do projeto

Status atual:

```text
Dia 1 concluído
```

Funcionalidades implementadas:

- projeto Android criado;
- Kotlin configurado;
- Jetpack Compose ativo;
- tela inicial criada;
- emulador configurado;
- projeto versionado com Git;
- repositório conectado ao GitHub.

---

## Próximo passo

No **Dia 2**, o projeto evolui com os fundamentos de Kotlin e a criação da primeira classe de domínio:

```kotlin
data class Moto(
    val id: Int,
    val nome: String,
    val modelo: String
)
```

Essa classe será a base para representar as motos dentro do aplicativo.

---
 
