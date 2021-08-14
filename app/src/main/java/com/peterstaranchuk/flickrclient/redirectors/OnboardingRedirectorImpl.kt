package com.peterstaranchuk.flickrclient.redirectors

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.peterstaranchuk.flickrclient.R
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingRedirector

class OnboardingRedirectorImpl : OnboardingRedirector {

    override fun redirectToAuthScreen(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.action_onboardingFragment_to_authFragment)
    }

}