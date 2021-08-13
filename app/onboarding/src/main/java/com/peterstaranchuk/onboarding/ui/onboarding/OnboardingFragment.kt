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
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.peterstaranchuk.onboarding.R

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val vm: OnboardingViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onboardingPager.adapter = OnboardingStatementsAdapter(
            fragment = this,
            titles = resources.getStringArray(R.array.titles),
            descriptions = resources.getStringArray(R.array.descriptions))

        TabLayoutMediator(binding.tabLayout, binding.onboardingPager){tab, position -> }.attach()
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