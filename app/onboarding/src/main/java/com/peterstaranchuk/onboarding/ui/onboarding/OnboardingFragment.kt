package com.peterstaranchuk.onboarding.ui.onboarding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.peterstaranchuk.common.BaseFragment
import com.peterstaranchuk.onboarding.R
import com.peterstaranchuk.onboarding.databinding.FragmentOnboardingBinding
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingRedirector
import com.peterstaranchuk.onboarding.ui.onboarding.statements.OnboardingStatementsAdapter
import org.koin.android.ext.android.inject

class OnboardingFragment : BaseFragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val vm: OnboardingViewModel by inject()
    private val redirector : OnboardingRedirector by inject()
    override val dependenciesModule = onboardingModule

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnboardingStatements()
        viewLifecycleOwner.lifecycle.addObserver(binding.mainAction)

        vm.eventSender.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is OnboardingContract.Event.EnableLoadingState -> binding.mainAction.setLoadingState()
                is OnboardingContract.Event.DisableLoadingState -> binding.mainAction.setDefaultState()
                is OnboardingContract.Event.OpenAuthScreen -> openAuthScreen(event.authLink)
                is OnboardingContract.Event.ShowGeneralError -> Snackbar.make(binding.root, getString(R.string.error_general), Snackbar.LENGTH_LONG).show()
                is OnboardingContract.Event.ShowNoBrowserError -> Snackbar.make(binding.root, getString(R.string.error_no_browser), Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun openAuthScreen(authLink: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(authLink)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            vm.showNoBrowserError()
        }
    }

    private fun setOnboardingStatements() {
        binding.onboardingPager.adapter = OnboardingStatementsAdapter(
            fragment = this,
            titles = resources.getStringArray(R.array.titles),
            descriptions = resources.getStringArray(R.array.descriptions)
        )

        TabLayoutMediator(binding.tabLayout, binding.onboardingPager) { _, _ -> }.attach()
    }
}