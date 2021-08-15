package com.peterstaranchuk.onboarding.ui.onboarding

import com.peterstaranchuk.onboarding.ui.auth.AuthInteractor
import com.peterstaranchuk.onboarding.ui.auth.AuthInteractorImpl
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingTimeRetriever
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingTimeRetrieverImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel { OnboardingViewModel(get()) }
    factory<OnboardingTimeRetriever> { OnboardingTimeRetrieverImpl() }
}