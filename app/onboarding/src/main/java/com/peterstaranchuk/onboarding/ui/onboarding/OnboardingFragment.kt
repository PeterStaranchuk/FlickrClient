package com.peterstaranchuk.onboarding.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peterstaranchuk.onboarding.databinding.FragmentOnboardingBinding
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val vm: OnboardingViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        binding.vm = vm
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(onboardingModule)
    }

    override fun onDestroy() {
        unloadKoinModules(onboardingModule)
        super.onDestroy()
    }
}