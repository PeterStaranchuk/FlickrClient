package com.peterstaranchuk.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterstaranchuk.onboarding.ui.onboarding.OnboardingViewModel
import com.peterstaranchuk.onboarding.ui.onboarding.statements.OnboardingContract
import kotlinx.coroutines.Dispatchers
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

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var vm: OnboardingViewModel

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)
        vm = OnboardingViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun main_action_button_should_go_to_the_loading_state_when_clicked() = runBlocking {
        vm.onMainActionButtonClicked()

        assertEquals(OnboardingContract.Event.EnableLoadingState, vm.screenEvent.receive())
    }

    @Test
    fun should_open_account_enter_screen_after_delay_when_main_action_button_clicked() = runBlockingTest {
        vm.onMainActionButtonClicked()
        this.advanceTimeBy(2000)
        assertEquals(OnboardingContract.Event.EnableLoadingState, vm.screenEvent.receive())
    }

}