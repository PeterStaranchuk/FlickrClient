package com.peterstaranchuk.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterstaranchuk.common.getOrAwaitValue
import com.peterstaranchuk.onboarding.ui.onboarding.OnboardingViewModel
import com.peterstaranchuk.onboarding.ui.onboarding.helpers.OnboardingTimeRetriever
import com.peterstaranchuk.onboarding.ui.onboarding.statements.OnboardingContract
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OnboardingViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var timeRetriever : OnboardingTimeRetriever

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var vm: OnboardingViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        vm = OnboardingViewModel(timeRetriever)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun main_action_button_should_go_to_the_loading_state_when_clicked() = runBlocking {
        every { timeRetriever.getDelayBeforeRedirect() } returns 2000

        vm.onMainActionButtonClicked()

        assertEquals(OnboardingContract.Event.EnableLoadingState, vm.screenEvent.getOrAwaitValue())
    }

    @Test
    fun should_open_account_enter_screen_after_delay_when_main_action_button_clicked() = runBlocking {
        every { timeRetriever.getDelayBeforeRedirect() } returns 50

        vm.onMainActionButtonClicked()

        delay(150)
        assertEquals(OnboardingContract.Event.RedirectToAccountEnterScreen, vm.screenEvent.value)
    }

}