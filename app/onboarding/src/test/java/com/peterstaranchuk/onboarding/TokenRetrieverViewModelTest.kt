package com.peterstaranchuk.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterstaranchuk.common.DispatchersProviderImpl
import com.peterstaranchuk.common.ScreenEventSender
import com.peterstaranchuk.onboarding.ui.token_retriever.TokenRetrieverContract
import com.peterstaranchuk.onboarding.ui.token_retriever.TokenRetrieverInteractor
import com.peterstaranchuk.onboarding.ui.token_retriever.TokenRetrieverViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TokenRetrieverViewModelTest {

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxUnitFun = true)
    private lateinit var interactor: TokenRetrieverInteractor

    @MockK(relaxUnitFun = true)
    private lateinit var eventSender: ScreenEventSender<TokenRetrieverContract.Event>

    private val dispatchersProvider = DispatchersProviderImpl(coroutineDispatcher, coroutineDispatcher)

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var vm: TokenRetrieverViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        vm = TokenRetrieverViewModel(interactor, eventSender, dispatchersProvider)
    }

    @After
    fun after() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun should_request_new_token_when_auth_deeplink_obtained() = runBlockingTest {
        coEvery { interactor.getToken("link") } returns flowOf("token")

        vm.onAuthFinished("link")

        coVerify {
            interactor.getToken("link")
        }
    }

    @Test
    fun should_save_token_when_it_is_received() = runBlockingTest {
        coEvery { interactor.getToken("link") } returns flowOf("token")

        vm.onAuthFinished("link")

        coVerify {
            interactor.saveToken("token")
        }
    }
}