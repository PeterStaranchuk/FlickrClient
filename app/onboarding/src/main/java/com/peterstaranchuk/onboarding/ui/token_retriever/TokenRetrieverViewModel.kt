package com.peterstaranchuk.onboarding.ui.token_retriever

import android.util.Log
import com.peterstaranchuk.common.BaseViewModel
import com.peterstaranchuk.common.DispatchersProvider
import com.peterstaranchuk.common.ScreenEventSender
import com.peterstaranchuk.onboarding.ui.AuthService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

class TokenRetrieverViewModel(
    private val interactor: TokenRetrieverInteractor,
    eventSender: ScreenEventSender<TokenRetrieverContract.Event>,
    dispatchers: DispatchersProvider
) : BaseViewModel<TokenRetrieverContract.Event>(eventSender, dispatchers) {

    fun onAuthFinished(deeplink: String) {
        launchIO {
            interactor.getToken(deeplink)
                .flowOn(dispatchers.io)
                .catch { e ->
                    Log.d("tag", e.message.toString())
                    sendUiEvent(TokenRetrieverContract.Event.RedirectToOnboarding)
                }
                .collect { newToken ->
                    interactor.saveToken(newToken)
                }
        }
    }

    //provide dependency directly because koin not support injection of scoped
    // instances directly in constructor at this moment
    fun provideAuthService(authService: AuthService) {
        interactor.provideAuthService(authService)
    }


}