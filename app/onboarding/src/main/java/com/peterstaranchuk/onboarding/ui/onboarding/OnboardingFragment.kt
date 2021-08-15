package com.peterstaranchuk.onboarding.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.peterstaranchuk.onboarding.R
import com.peterstaranchuk.onboarding.databinding.FragmentOnboardingBinding
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingRedirector
import com.peterstaranchuk.onboarding.ui.onboarding.statements.OnboardingStatementsAdapter
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val vm: OnboardingViewModel by inject()
    private val redirector : OnboardingRedirector by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnboardingStatements()
        viewLifecycleOwner.lifecycle.addObserver(binding.mainAction)

        vm.screenEvent.observe(viewLifecycleOwner, Observer { event ->
                when (event) {
                    OnboardingContract.Event.EnableLoadingState -> binding.mainAction.setLoadingState()
                    OnboardingContract.Event.RedirectToAccountEnterScreen -> {
                        redirector.redirectToAuthScreen(this@OnboardingFragment)
                    }
                }
            })
    }

    private fun setOnboardingStatements() {
        binding.onboardingPager.adapter = OnboardingStatementsAdapter(
            fragment = this,
            titles = resources.getStringArray(R.array.titles),
            descriptions = resources.getStringArray(R.array.descriptions)
        )

        TabLayoutMediator(binding.tabLayout, binding.onboardingPager) { _, _ -> }.attach()
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