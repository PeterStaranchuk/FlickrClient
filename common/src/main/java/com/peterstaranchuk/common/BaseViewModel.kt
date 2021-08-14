package com.peterstaranchuk.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.redirectors.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

open class BaseViewModel<E : Event> : ViewModel() {

    val screenEvent : Channel<E> = Channel()

    fun sendEvent(event : E)  {
        viewModelScope.launch(Dispatchers.Main) {
            screenEvent.send(event)
        }
    }
}