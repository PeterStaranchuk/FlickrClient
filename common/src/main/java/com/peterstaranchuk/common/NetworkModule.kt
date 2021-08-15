package com.peterstaranchuk.common

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(NetworkConstants.CONNECT_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .writeTimeout(NetworkConstants.WRITE_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .readTimeout(NetworkConstants.READ_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .addNetworkInterceptor(get(named("logs")))
            .build()
    }

    single<Interceptor>(named("logs")) {
        HttpLoggingInterceptor {
            Timber.d("Network: $it")
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single { Gson() }
}