package com.peterstaranchuk.onboarding.ui.auth

import com.peterstaranchuk.onboarding.ui.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface OnboardingInteractor {
    fun getAuthUrl(): Flow<String>
    fun provideAuthService(authService: AuthService)
}

class OnboardingInteractorImpl : OnboardingInteractor {

    private lateinit var authService: AuthService

    override fun getAuthUrl(): Flow<String> {
        return flow {
            emit(authService.getAuthUrl())
        }
    }

    override fun provideAuthService(authService: AuthService) {
        this.authService = authService
    }

}