package com.peterstaranchuk.onboarding.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.peterstaranchuk.common.redirectors.SplashRedirector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashFragment : Fragment() {

    private val splashRedirector: SplashRedirector by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SplashScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                splashRedirector.redirectToOnboarding(this@SplashFragment)
            }
        }
    }
}