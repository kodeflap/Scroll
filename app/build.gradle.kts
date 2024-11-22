import com.android.build.api.variant.BuildConfigField

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "kodeflap.com.scroll"
    compileSdk = 35

    defaultConfig {
        applicationId = "kodeflap.com.scroll"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding  = true
    }
    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://dummyjson.com\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.8.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.8.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.5.0")
    // Koin
    implementation("io.insert-koin:koin-android:4.0.0")
    //Kodein Dependency Injection
    implementation ("org.kodein.di:kodein-di-generic-jvm:6.2.1")
    implementation("org.kodein.di:kodein-di-framework-android-x:6.2.1")

    //Navigation
    implementation ("androidx.navigation:navigation-runtime-ktx:2.3.0-alpha05")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.0-alpha05")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0-alpha05")

    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-reactivestreams:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.2.0")

    //For Image Loading
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")

    //Material design
    implementation ("com.google.android.material:material:1.2.0-alpha06")
}