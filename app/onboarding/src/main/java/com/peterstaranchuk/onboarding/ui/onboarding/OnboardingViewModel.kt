package com.peterstaranchuk.onboarding.ui.onboarding

import com.peterstaranchuk.common.BaseViewModel
import com.peterstaranchuk.common.DispatchersProvider
import com.peterstaranchuk.common.ScreenEventSender
import com.peterstaranchuk.onboarding.ui.auth.OnboardingInteractor
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class OnboardingViewModel(
    private val interactor: OnboardingInteractor,
    dispatchersHolder: DispatchersProvider,
    eventSender: ScreenEventSender<OnboardingContract.Event>
) : BaseViewModel<OnboardingContract.Event>(eventSender, dispatchersHolder) {

    fun onMainActionButtonClicked() {
        sendUiEvent(OnboardingContract.Event.EnableLoadingState)
        launchIO {
            interactor.getAuthUrl()
                .catch {
                    sendUiEvent(OnboardingContract.Event.ShowGeneralError)
                    sendUiEvent(OnboardingContract.Event.DisableLoadingState)
                }
                .collect { authLink ->
                    sendUiEvent(OnboardingContract.Event.OpenAuthScreen(authLink))
                    sendUiEvent(OnboardingContract.Event.DisableLoadingState)
                }
        }
    }

    fun showNoBrowserError() {
        sendUiEvent(OnboardingContract.Event.ShowNoBrowserError)
        sendUiEvent(OnboardingContract.Event.DisableLoadingState)
    }
}