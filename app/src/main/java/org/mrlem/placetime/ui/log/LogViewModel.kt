package org.mrlem.placetime.ui.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.mrlem.placetime.PlaceTimeApplication.Companion.eventRepository
import org.mrlem.placetime.common.BaseViewModel
import org.mrlem.placetime.core.domain.model.EventAndPlace

class LogViewModel : BaseViewModel() {

    private val _events = MutableLiveData(emptyList<EventAndPlace>())
    val events = _events as LiveData<List<EventAndPlace>>

    init {
        eventRepository
            .list()
            .doOnNext { _events.postValue(it) }
            .bind()
    }
}
