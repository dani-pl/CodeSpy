import com.google.firebase.appdistribution.gradle.AppDistributionExtension

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.firebase.google.services)
    alias(libs.plugins.firebase.appdistribution)
}

android {
    namespace = "com.danipl.codespy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.danipl.codespy"
        minSdk = 24
        targetSdk = 29
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false

            signingConfig = signingConfigs.getByName("debug")
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            signingConfig = signingConfigs.getByName("debug")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard_release.pro"
            )

            configure<AppDistributionExtension> {
                groups = "internal-testers"
                releaseNotesFile = findProperty("releaseNotesFile")?.toString().orEmpty()
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.hilt.android)
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.timber)
    
    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    // Coil
    implementation(libs.coil)

    // Serialization
    implementation(libs.kotlin.serialization)

    // Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.constraint.layout.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.android)

    // Data Store
    implementation(libs.data.store)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)


    kapt(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}