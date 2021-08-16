package com.peterstaranchuk.common

import androidx.lifecycle.LiveData
import com.peterstaranchuk.common.redirectors.UiEvent

interface ScreenEventSender<E : UiEvent> {
    val event: LiveData<E>
    fun sendEvent(event: E)
}

class ScreenEventSenderImpl<E : UiEvent> : ScreenEventSender<E> {
    private val _screenEvent = SingleLiveEvent<E>()

    override val event : LiveData<E> = _screenEvent

    override fun sendEvent(event: E) {
        _screenEvent.value = event
    }

}