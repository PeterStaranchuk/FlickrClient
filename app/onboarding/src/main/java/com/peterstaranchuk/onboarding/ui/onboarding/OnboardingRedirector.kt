package com.peterstaranchuk.onboarding.ui.onboarding

import androidx.fragment.app.Fragment

interface OnboardingRedirector {
    fun redirectToAuthScreen(fragment: Fragment)
}