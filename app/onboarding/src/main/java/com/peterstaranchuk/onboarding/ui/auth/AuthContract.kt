package com.peterstaranchuk.onboarding.ui.auth

import com.peterstaranchuk.common.UiState
import com.peterstaranchuk.common.redirectors.UiEvent

class AuthContract {

    sealed class State : UiState {
        object Loading : State()
        object RedirectToNextScreen : State()
        class Auth(val authLink : String) : State()
    }

    sealed class Event : UiEvent {
        object CloseScreen : Event()
    }
}