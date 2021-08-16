package com.peterstaranchuk.flickrclient.redirectors

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.peterstaranchuk.flickrclient.R
import com.peterstaranchuk.onboarding.ui.token_retriever.TokenRetrieverRedirector

class TokenRetrieverRedirectorImpl : TokenRetrieverRedirector {
    override fun redirectToOnboarding(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.action_tokenRetrieverFragment_to_onboardingFragment)
    }

    override fun redirectToMainScreen(fragment: Fragment) {
        //todo redirect to main screen
    }

}