plugins {
    // Plugin principal da aplicação Android.
    alias(libs.plugins.android.application)

    // Plugin para usar Kotlin no Android.
    alias(libs.plugins.kotlin.android)

    // Plugin para usar Jetpack Compose.
    alias(libs.plugins.kotlin.compose)

    // Plugin KSP necessário para o Room.
    alias(libs.plugins.ksp)

    alias(libs.plugins.hilt.android)
}

android {
    // Define o namespace do app.
    namespace = "com.laiana.motosync"

    // Define a versão do SDK usada para compilar o app.
    compileSdk = 36

    defaultConfig {
        // Identificador único do aplicativo.
        applicationId = "com.laiana.motosync"

        // Versão mínima do Android suportada.
        minSdk = 24

        // Versão alvo do Android.
        targetSdk = 36

        // Código interno da versão do app.
        versionCode = 1

        // Nome visível da versão do app.
        versionName = "1.0"

        // Runner usado para testes instrumentados.
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Desativa minificação no build release por enquanto.
            isMinifyEnabled = false

            // Define arquivos de regras do ProGuard.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Define compatibilidade com Java 11.
        sourceCompatibility = JavaVersion.VERSION_11

        // Define compatibilidade com Java 11.
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        // Define a JVM target do Kotlin.
        jvmTarget = "11"
    }

    buildFeatures {
        // Ativa o Jetpack Compose.
        compose = true
    }
}

dependencies {
    // Hilt - injeção de dependência
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Biblioteca base do Android com extensões Kotlin.
    implementation(libs.androidx.core.ktx)

    // Biblioteca de ciclo de vida.
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ViewModel para Jetpack Compose.
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Integra Activity com Jetpack Compose.
    implementation(libs.androidx.activity.compose)

    // Navigation Compose para navegação entre telas.
    implementation(libs.androidx.navigation.compose)

    // BOM do Compose para controlar versões das bibliotecas Compose.
    implementation(platform(libs.androidx.compose.bom))

    // Biblioteca principal de UI do Compose.
    implementation(libs.androidx.compose.ui)

    // Biblioteca de gráficos do Compose.
    implementation(libs.androidx.compose.ui.graphics)

    // Preview de telas Compose no Android Studio.
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Material 3 para componentes visuais modernos.
    implementation(libs.androidx.compose.material3)

    // Runtime principal do Room.
    implementation(libs.androidx.room.runtime)

    // Suporte Kotlin, Coroutines e Flow para Room.
    implementation(libs.androidx.room.ktx)

    // Compilador do Room usando KSP.
    ksp(libs.androidx.room.compiler)

    // Biblioteca de testes unitários.
    testImplementation(libs.junit)

    // Biblioteca de testes Android com JUnit.
    androidTestImplementation(libs.androidx.junit)

    // Biblioteca de testes de interface Android.
    androidTestImplementation(libs.androidx.espresso.core)

    // BOM do Compose para testes.
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Testes de UI do Compose.
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    androidTestImplementation("androidx.navigation:navigation-testing:2.7.0")

    androidTestImplementation("androidx.test:core:1.5.0")
    // Ferramentas de debug do Compose.
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Manifest de testes/debug do Compose.
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}