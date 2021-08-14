package com.peterstaranchuk.onboarding.ui.onboarding.helpers

import androidx.fragment.app.Fragment

interface OnboardingRedirector {
    fun redirectToAuthScreen(fragment: Fragment)
}