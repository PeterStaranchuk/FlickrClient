package com.peterstaranchuk.onboarding.ui.onboarding

import com.peterstaranchuk.common.redirectors.UiEvent

class OnboardingContract {
    sealed class Event : UiEvent {
        object EnableLoadingState : Event()
        object DisableLoadingState : Event()
        object ShowNoBrowserError : Event()
        object ShowGeneralError : Event()
        data class OpenAuthScreen(val authLink : String) : Event()
    }
}