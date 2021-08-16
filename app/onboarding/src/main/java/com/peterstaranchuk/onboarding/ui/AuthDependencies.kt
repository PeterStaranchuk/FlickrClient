package com.peterstaranchuk.onboarding.ui

import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {
    scope(named("auth_scope")) {
        scoped<AuthService> { AuthServiceImpl() }
    }
}