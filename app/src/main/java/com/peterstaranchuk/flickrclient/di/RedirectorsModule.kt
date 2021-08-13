package com.peterstaranchuk.flickrclient.di

import com.peterstaranchuk.common.redirectors.SplashRedirector
import com.peterstaranchuk.flickrclient.redirectors.SplashRedirectorImpl
import org.koin.dsl.module

val redirectorsModule = module {
    factory<SplashRedirector> { SplashRedirectorImpl() }
}