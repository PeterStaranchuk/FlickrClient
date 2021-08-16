package com.peterstaranchuk.common

import androidx.lifecycle.LiveData
import com.peterstaranchuk.common.redirectors.UiEvent

//even though the presence of this "middle man" seems redundant
//it provides the way to have 100% on testing of UI events (call order, called/not called etc)
interface ScreenEventSender<E : UiEvent> {
    val event: LiveData<E>
    fun sendUiEvent(event: E)
}

class ScreenEventSenderImpl<E : UiEvent> : ScreenEventSender<E> {
    private val _screenEvent = SingleLiveEvent<E>()

    override val event : LiveData<E> = _screenEvent

    override fun sendUiEvent(event: E) {
        _screenEvent.value = event
    }

}