package com.peterstaranchuk.onboarding.ui.token_retriever

import com.peterstaranchuk.common.redirectors.UiEvent

class TokenRetrieverContract {

    sealed class Event : UiEvent {
        object RedirectToOnboarding : Event()
        object RedirectToMainScreen : Event()
    }
}