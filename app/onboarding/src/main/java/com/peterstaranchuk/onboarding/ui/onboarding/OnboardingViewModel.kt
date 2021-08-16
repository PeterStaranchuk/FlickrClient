package com.peterstaranchuk.onboarding.ui.onboarding

import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.BaseViewModel
import com.peterstaranchuk.common.ScreenEventSender
import com.peterstaranchuk.onboarding.ui.auth.OnboardingInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val interactor: OnboardingInteractor,
    eventSender: ScreenEventSender<OnboardingContract.Event>
) : BaseViewModel<OnboardingContract.Event>(eventSender) {

    fun onMainActionButtonClicked() {
        sendEvent(OnboardingContract.Event.EnableLoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getAuthUrl()
                .catch {
                    sendEvent(OnboardingContract.Event.ShowGeneralError)
                }
                .collect { authLink ->
                    launch(Dispatchers.Main) {
                        sendEvent(OnboardingContract.Event.OpenAuthScreen(authLink))
                        sendEvent(OnboardingContract.Event.DisableLoadingState)
                    }
                }
        }
    }

    fun showNoBrowserError() {
        sendEvent(OnboardingContract.Event.ShowNoBrowserError)
    }
}