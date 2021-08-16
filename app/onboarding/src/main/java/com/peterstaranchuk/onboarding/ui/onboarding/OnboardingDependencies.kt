package com.peterstaranchuk.onboarding.ui.onboarding

import com.peterstaranchuk.common.ScreenEventSender
import com.peterstaranchuk.common.ScreenEventSenderImpl
import com.peterstaranchuk.onboarding.ui.auth.OnboardingInteractor
import com.peterstaranchuk.onboarding.ui.auth.OnboardingInteractorImpl
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingTimeRetriever
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingTimeRetrieverImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel { OnboardingViewModel(get(), get()) }
    factory<OnboardingTimeRetriever> { OnboardingTimeRetrieverImpl() }
    factory<OnboardingInteractor> { OnboardingInteractorImpl() }
    factory<ScreenEventSender<OnboardingContract.Event>> { ScreenEventSenderImpl() }
}