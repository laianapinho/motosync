// Arquivo Gradle principal do projeto.
// Aqui ficam os plugins compartilhados pelos módulos.

plugins {
    // Plugin principal para projetos Android.
    alias(libs.plugins.android.application) apply false

    // Plugin para usar Kotlin no Android.
    alias(libs.plugins.kotlin.android) apply false

    // Plugin necessário para usar Jetpack Compose com Kotlin.
    alias(libs.plugins.kotlin.compose) apply false

    // Plugin KSP usado pelo Room para gerar código automaticamente.
    alias(libs.plugins.ksp) apply false
}