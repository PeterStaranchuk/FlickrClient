package com.peterstaranchuk.onboarding.ui.onboarding

import com.peterstaranchuk.common.redirectors.UiEvent

class OnboardingContract {
    sealed class Event : UiEvent {
        object EnableLoadingState : Event()
        object RedirectToAccountEnterScreen : Event()
    }
}