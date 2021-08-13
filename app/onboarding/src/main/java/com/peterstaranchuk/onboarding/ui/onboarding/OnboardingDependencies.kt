package com.peterstaranchuk.onboarding.ui.onboarding

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel { OnboardingViewModel() }
}