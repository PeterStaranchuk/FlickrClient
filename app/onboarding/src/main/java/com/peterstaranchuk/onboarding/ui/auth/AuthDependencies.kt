package com.peterstaranchuk.onboarding.ui.auth

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    viewModel { AuthViewModel(get()) }
    factory<AuthInteractor> { AuthInteractorImpl(get()) }
    factory<AuthApi> {
        val retrofit : Retrofit = get()
        retrofit.create(AuthApi::class.java)
    }
}