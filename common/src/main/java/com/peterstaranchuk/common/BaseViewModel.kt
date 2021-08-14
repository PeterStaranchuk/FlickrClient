package com.peterstaranchuk.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterstaranchuk.common.redirectors.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel<E : Event> : ViewModel() {

    private val _screenEvent = MutableLiveData<E>()

    val screenEvent: LiveData<E> = _screenEvent

    fun sendEvent(event: E) {
        viewModelScope.launch(Dispatchers.Main) {
            _screenEvent.value = event
        }
    }
}