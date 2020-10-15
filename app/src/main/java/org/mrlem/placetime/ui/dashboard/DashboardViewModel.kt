package org.mrlem.placetime.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.mrlem.placetime.PlaceTimeApplication.Companion.placeRepository
import org.mrlem.placetime.common.BaseViewModel

class DashboardViewModel : BaseViewModel() {

    private val _place = MutableLiveData("Work")
    val place = _place as LiveData<String>

    private val _timeToday = MutableLiveData("0:00:00")
    val timeToday = _timeToday as LiveData<String>

    init {
        placeRepository.current()
            .doOnSuccess { _place.postValue(it.label) }
            .subscribe()
    }

    // TODO - calculate & update stats
}
