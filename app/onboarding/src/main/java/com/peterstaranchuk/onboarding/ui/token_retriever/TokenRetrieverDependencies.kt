package com.peterstaranchuk.onboarding.ui.token_retriever

import com.peterstaranchuk.common.ScreenEventSender
import com.peterstaranchuk.common.ScreenEventSenderImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tokenRetrieverModule = module {
    viewModel { TokenRetrieverViewModel(get(), get(), get()) }
    factory<TokenRetrieverInteractor> { TokenRetrieverInteractorImpl() }
    factory<ScreenEventSender<TokenRetrieverContract.Event>> { ScreenEventSenderImpl() }
}