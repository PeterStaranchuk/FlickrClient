package com.peterstaranchuk.onboarding.ui.token_retriever

import com.peterstaranchuk.common.ScreenEventSender
import com.peterstaranchuk.common.ScreenEventSenderImpl
import com.peterstaranchuk.common.TokenStoreManager
import com.peterstaranchuk.common.TokenStoreManagerImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tokenRetrieverModule = module {
    viewModel { TokenRetrieverViewModel(get(), get(), get()) }
    factory<TokenRetrieverInteractor> { TokenRetrieverInteractorImpl(get()) }
    factory<TokenStoreManager> { TokenStoreManagerImpl(get()) }
    factory<ScreenEventSender<TokenRetrieverContract.Event>> { ScreenEventSenderImpl() }
}