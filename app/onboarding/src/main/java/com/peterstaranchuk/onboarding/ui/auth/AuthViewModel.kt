package com.peterstaranchuk.onboarding.ui.auth

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class AuthViewModel(private val interactor: AuthInteractor) : BaseViewModel<AuthContract.Event>() {

    private val _state = MutableStateFlow<AuthContract.State>(AuthContract.State.Loading)

    val state: StateFlow<AuthContract.State> = _state

    fun onCloseIconClicked() {
        sendEvent(AuthContract.Event.CloseScreen)
    }

    fun fetchAuthUrl() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                interactor.getAuthUrl()
                    .collect {
                        _state.value = AuthContract.State.Auth(authLink = it)
                    }
            } catch (e : Exception) {
                Log.d("Error", e.message.toString())
            }

        }
    }

}