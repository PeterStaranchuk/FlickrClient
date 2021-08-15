package com.peterstaranchuk.onboarding.ui.onboarding

import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.BaseViewModel
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingTimeRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingViewModel(private val timeRetriever: OnboardingTimeRetriever) : BaseViewModel<OnboardingContract.Event>() {

    fun onMainActionButtonClicked() {
        viewModelScope.launch(Dispatchers.Main) {
            sendEvent(OnboardingContract.Event.EnableLoadingState)

            delay(timeRetriever.getDelayBeforeRedirect())

            sendEvent(OnboardingContract.Event.RedirectToAccountEnterScreen)
        }
    }
}