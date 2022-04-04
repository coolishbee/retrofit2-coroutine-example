# retrofit2-coroutine-example
Retrofit2, Coroutine usage on Kotlin app

## Features

- GET, POST HTTP Networking

## Dependencies

```groovy
dependencies {
    // Kotlin & Coroutines
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"

    // Retrofit2
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.4.0'
}
```

## Backend

[go-gin-sample](https://github.com/coolishbee/go-gin-sample) is the simple RESTful API server.