package com.peterstaranchuk.onboarding.ui.onboarding

import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.BaseViewModel
import com.peterstaranchuk.onboarding.ui.onboarding.statements.OnboardingContract
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingViewModel : BaseViewModel<OnboardingContract.Event>() {

    fun onMainActionButtonClicked() {
        viewModelScope.launch {
            sendEvent(OnboardingContract.Event.EnableLoadingState)

            delay(2000)

            sendEvent(OnboardingContract.Event.RedirectToAccountEnterScreen)
        }
    }
}