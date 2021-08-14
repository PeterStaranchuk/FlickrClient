package com.peterstaranchuk.flickrclient.di

import com.peterstaranchuk.flickrclient.redirectors.OnboardingRedirectorImpl
import com.peterstaranchuk.flickrclient.redirectors.SplashRedirectorImpl
import com.peterstaranchuk.onboarding.ui.onboarding.OnboardingRedirector
import com.peterstaranchuk.onboarding.ui.splash.SplashRedirector
import org.koin.dsl.module

val redirectorsModule = module {
    factory<SplashRedirector> { SplashRedirectorImpl() }
    factory<OnboardingRedirector> { OnboardingRedirectorImpl() }
}