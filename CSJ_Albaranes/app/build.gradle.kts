plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.csj_albaranes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.csj_albaranes"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.github.yuriy-budiyev:code-scanner:2.3.2")
    implementation("org.mongodb:bson:4.10.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.amazonaws:aws-android-sdk-s3:2.17.1")
    //implementation("io.swagger:swagger-java-client:swagger-java-client:compile")
    //implementation("io.swagger:swagger-codegen-maven-plugin:2.4.39")
    //implementation("io.swagger.codegen.v3:swagger-codegen-maven-plugin:3.0.54")
}