    plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.jetbrains.kotlin.android)
        alias(libs.plugins.gms)
        id ("kotlin-kapt")
//        kotlin("kapt") version "1.8.20"
    }

    android {
        namespace = "com.example.signalapp"
        compileSdk = 34

        defaultConfig {
            applicationId = "com.example.signalapp"
            minSdk = 24
            targetSdk = 34
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
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

        val room_version = "2.4.2"
        implementation("androidx.room:room-ktx:$room_version")
        kapt("androidx.room:room-compiler:$room_version")
        implementation ("androidx.room:room-runtime:$room_version")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

        implementation (libs.navigator)
        implementation(platform(libs.firebase.boom))
        implementation(libs.firebase.firestore)
        implementation(libs.coil.compose)
        implementation(libs.firebase.storage)
        implementation (libs.shimmer)
        implementation (libs.dataStore)
        implementation (libs.kotlinx.serialization.json)
        implementation (libs.androidx.lifecycle.viewmodel.compose)
        implementation (libs.androidx.runtime.livedata)
        implementation (libs.runtime.livedata)
        implementation(libs.accompanist.pager.indicators)


        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.datastore.core.android)
        implementation(libs.androidx.datastore.preferences.core.jvm)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }