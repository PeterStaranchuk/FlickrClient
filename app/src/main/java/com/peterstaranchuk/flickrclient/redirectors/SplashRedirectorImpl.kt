package com.peterstaranchuk.flickrclient.redirectors

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.peterstaranchuk.common.redirectors.SplashRedirector
import com.peterstaranchuk.flickrclient.R

class SplashRedirectorImpl : SplashRedirector {
    override fun redirectToOnboarding(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
    }

}