package com.peterstaranchuk.onboarding.ui.onboarding.helpers

interface OnboardingTimeRetriever {
    fun getDelayBeforeRedirect() : Long
}

class OnboardingTimeRetrieverImpl : OnboardingTimeRetriever {

    override fun getDelayBeforeRedirect() = 2000L

}