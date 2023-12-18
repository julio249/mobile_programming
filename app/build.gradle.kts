plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.final_project"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.final_project"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
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
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("org.json:json:20210307")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0") // Replace '2.x.x' with the actual version
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Replace '2.x.x' with the actual version
    implementation ("com.squareup.okhttp3:okhttp:4.10.0") // Replace '4.x.x' with the actual version
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation ("com.android.volley:volley:1.2.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation("com.google.android.libraries.places:places:3.2.0")
    implementation ("org.simpleframework:simple-xml:2.7.1")
    implementation ("com.squareup.retrofit2:converter-simplexml:2.9.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.google.firebase:firebase-firestore:24.9.1")

    implementation("de.hdodenhof:circleimageview:3.0.1")
    //FireBase Essential
    implementation ("com.google.firebase:firebase-auth:22.2.0")

    //FireBase Essential
    implementation("com.google.firebase:firebase-storage:20.3.0")

    implementation("com.google.firebase:firebase-messaging:23.3.1")



}









