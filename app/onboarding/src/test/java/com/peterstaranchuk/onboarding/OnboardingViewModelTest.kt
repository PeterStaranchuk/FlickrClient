package com.peterstaranchuk.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterstaranchuk.common.ScreenEventSender
import com.peterstaranchuk.onboarding.ui.auth.OnboardingInteractor
import com.peterstaranchuk.onboarding.ui.onboarding.OnboardingContract
import com.peterstaranchuk.onboarding.ui.onboarding.OnboardingViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OnboardingViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var interactor: OnboardingInteractor

    @MockK(relaxed = true)
    private lateinit var eventSender: ScreenEventSender<OnboardingContract.Event>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var vm: OnboardingViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        vm = OnboardingViewModel(interactor, eventSender)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun main_action_button_should_go_to_the_loading_state_when_clicked() = runBlocking {
        coEvery { interactor.getAuthUrl() } returns flow {}

        vm.onMainActionButtonClicked()

        coVerify {
            eventSender.sendEvent(OnboardingContract.Event.EnableLoadingState)
        }
    }

    @Test
    fun should_open_account_enter_screen_when_main_action_button_clicked() = runBlocking {
        coEvery { interactor.getAuthUrl() } returns flowOf("link")

        vm.onMainActionButtonClicked()

        coVerify {
            eventSender.sendEvent(OnboardingContract.Event.OpenAuthScreen("link"))
        }
    }

    @Test
    fun should_disable_loading_state_when_link_is_loaded() {
        coEvery { interactor.getAuthUrl() } returns flowOf("link")

        vm.onMainActionButtonClicked()

        coVerify {
            eventSender.sendEvent(OnboardingContract.Event.DisableLoadingState)
        }
    }

    @Test
    fun should_show_no_browser_error_when_user_has_no_browser() {
        vm.showNoBrowserError()

        coVerify {
            eventSender.sendEvent(OnboardingContract.Event.ShowNoBrowserError)
        }
    }

    @Test
    fun should_show_error_if_app_cant_get_a_auth_link() {
        coEvery { interactor.getAuthUrl() } returns flow {
            throw RuntimeException("Something went wrong")
        }

        vm.onMainActionButtonClicked()

        coVerify {
            eventSender.sendEvent(OnboardingContract.Event.ShowGeneralError)
        }
    }

}