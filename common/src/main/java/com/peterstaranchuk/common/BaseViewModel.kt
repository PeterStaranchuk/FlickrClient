package com.peterstaranchuk.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.redirectors.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel<E : UiEvent> : ViewModel() {

    private val _screenEvent = SingleLiveEvent<E>()

    val screenEvent: LiveData<E> = _screenEvent

    fun sendEvent(event: E) {
        viewModelScope.launch(Dispatchers.Main) {
            _screenEvent.value = event
        }
    }
}