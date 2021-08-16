package com.peterstaranchuk.onboarding.ui.token_retriever

import com.peterstaranchuk.common.TokenStoreManager
import com.peterstaranchuk.onboarding.ui.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.KoinComponent

interface TokenRetrieverInteractor {
    suspend fun saveToken(newToken: String)
    fun getToken(deeplink: String): Flow<String>
    fun provideAuthService(authService: AuthService)
}

class TokenRetrieverInteractorImpl(private val tokenStoreManager: TokenStoreManager) : TokenRetrieverInteractor, KoinComponent {

    private lateinit var authService: AuthService

    override suspend fun saveToken(newToken: String) {
        tokenStoreManager.saveToken(newToken)
    }

    override fun getToken(deeplink: String): Flow<String> {
        return flow {
            emit(authService.getAccessToken(deeplink))
        }
    }

    override fun provideAuthService(authService: AuthService) {
        this.authService = authService
    }
}