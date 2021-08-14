package com.peterstaranchuk.onboarding.ui.onboarding.statements

class OnboardingContract {
    sealed class Event : com.peterstaranchuk.common.redirectors.Event {
        object EnableLoadingState : Event()
        object RedirectToAccountEnterScreen : Event()
    }
}