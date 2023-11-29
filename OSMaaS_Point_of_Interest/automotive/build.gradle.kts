import java.io.FileInputStream
import java.util.Properties

var keystorePropertiesFile = rootProject.file("automotive/keystore.properties")
if(!keystorePropertiesFile.exists()) {
    logger.error("keystore.properties not properly configured! Using template to pass tests")
    keystorePropertiesFile = rootProject.file("automotive/keystore.properties.template")
}

val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))


@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt") // Only for Kotlin projects.
    id("io.objectbox")
}
apply(from="../jacoco.gradle")

android {
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    namespace = "com.example.varbergpoi"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.polestar.hallandpoi.qa.android"
        minSdk = 29
        targetSdk = 33
        versionCode = 2
        versionName = "1.0-RC2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isDebuggable = true
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
            testCoverage.jacocoVersion = "0.8.8"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.androidx.app.automotive)
    implementation(libs.timber)
    implementation("com.opencsv:opencsv:5.9")

}