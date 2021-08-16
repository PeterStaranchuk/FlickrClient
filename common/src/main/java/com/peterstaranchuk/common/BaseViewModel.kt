package com.peterstaranchuk.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.redirectors.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


open class BaseViewModel<E : UiEvent>(val eventSender: ScreenEventSender<E>, val dispatchers: DispatchersProvider) : ViewModel() {

    fun sendUiEvent(event: E) {
        viewModelScope.launch(dispatchers.main) {
            eventSender.sendUiEvent(event)
        }
    }

    fun launchIO(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(dispatchers.io, start, block)
    }

    fun launchMain(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(dispatchers.main, start, block)
    }
}