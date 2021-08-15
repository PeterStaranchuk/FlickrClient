package com.peterstaranchuk.flickrclient.redirectors

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.peterstaranchuk.onboarding.ui.auth.AuthNavigator

class AuthNavigatorImpl : AuthNavigator {
    override fun goBack(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}