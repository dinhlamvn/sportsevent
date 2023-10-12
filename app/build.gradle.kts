plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
}

android {
    namespace = "com.inspius.sportsevent"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.inspius.sportsevent"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro", "retrofit2.pro"
            )
        }
    }

    flavorDimensions("default")
    productFlavors {
        create("develop") {
            applicationIdSuffix = ".dev"
            dimension = "default"
            buildConfigField(
                "String",
                "API_URL",
                "\"https://jmde6xvjr4.execute-api.us-east-1.amazonaws.com/\""
            )
        }

        create("production") {
            dimension = "default"
            buildConfigField(
                "String",
                "API_URL",
                "\"https://jmde6xvjr4.execute-api.us-east-1.amazonaws.com/\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    // Testing
    val junitVersion = "4.13.2"
    val mockitoVersion = "5.6.0"
    val mockitoKotlinVersion = "5.1.0"
    val coroutinesVersion = "1.6.4"
    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    // AndroidX
    val appcompatVersion = "1.6.1"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")

    val coreKtxVersion = "1.12.0"
    implementation("androidx.core:core-ktx:$coreKtxVersion")

    val ktxVersion = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$ktxVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$ktxVersion")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    // Android Design
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Google MD
    val materialVersion = "1.9.0"
    implementation("com.google.android.material:material:$materialVersion")

    // Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Glide
    val glideVersion = "4.13.2"
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")
}