package com.peterstaranchuk.onboarding.ui.token_retriever

import androidx.fragment.app.Fragment

interface TokenRetrieverRedirector {
    fun redirectToOnboarding(fragment: Fragment)
    fun redirectToMainScreen(fragment: Fragment)
}