package com.peterstaranchuk.onboarding.ui

import org.koin.core.qualifier.named
import org.koin.dsl.module

object AuthDependency {
    const val SCOPE_NAME = "auth_scope"
    const val SCOPE_ID = "auth_scope_id"
}

val authModule = module {
    scope(named(AuthDependency.SCOPE_NAME)) {
        scoped<AuthService> { AuthServiceImpl() }
    }
}