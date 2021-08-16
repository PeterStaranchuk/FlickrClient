package com.peterstaranchuk.common

import androidx.lifecycle.ViewModel
import com.peterstaranchuk.common.redirectors.UiEvent
import org.koin.android.ext.android.inject


open class BaseViewModel<E : UiEvent>(val eventSender : ScreenEventSender<E>) : ViewModel() {

    fun sendEvent(event : E) {
        eventSender.sendEvent(event)
    }
}