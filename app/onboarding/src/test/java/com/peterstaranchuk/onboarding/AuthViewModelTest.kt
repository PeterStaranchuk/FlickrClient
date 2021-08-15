package com.peterstaranchuk.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterstaranchuk.common.getOrAwaitValue
import com.peterstaranchuk.onboarding.ui.auth.AuthContract
import com.peterstaranchuk.onboarding.ui.auth.AuthViewModel
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var vm: AuthViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        vm = AuthViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun should_close_screen_when_close_button_clicked() {
        vm.onCloseIconClicked()

        Assert.assertEquals(AuthContract.Event.CloseScreen, vm.screenEvent.getOrAwaitValue())
    }

    @Test
    fun should_show_loading_state_when_auth_url_is_fetching() {
        vm.fetchAuthUrl()

        Assert.assertEquals(AuthContract.State.Loading, vm.state.getOrAwaitValue())
    }

    @Test
    fun should_redirect_to_next_screen_when_auth_is_successful() {

    }

    @Test
    fun should_receive_auth_link_when_screen_started() {

    }

    @Test
    fun should_save_user_token_info_when_user_auth_successfully() {

    }

    @Test
    fun should_show_error_when_link_cannot_be_fetched() {

    }

    @Test
    fun should_show_error_when_page_cannot_be_downloaded() {

    }



}