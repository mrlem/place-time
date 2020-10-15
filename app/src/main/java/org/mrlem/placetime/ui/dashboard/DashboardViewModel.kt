package org.mrlem.placetime.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import org.mrlem.placetime.PlaceTimeApplication.Companion.eventRepository
import org.mrlem.placetime.PlaceTimeApplication.Companion.placeRepository
import org.mrlem.placetime.common.BaseViewModel
import org.mrlem.placetime.core.domain.usecase.GetTimeThisDay

class DashboardViewModel : BaseViewModel() {

    // TODO - loading screen

    private val _place = MutableLiveData<String>()
    val place = _place as LiveData<String>

    private val _timeToday = MutableLiveData("")
    val timeToday = _timeToday as LiveData<String>

    init {
        placeRepository.current()
            .doOnNext { _place.postValue(it.label) }
            .bind()

        placeRepository.current()
            .distinctUntilChanged()
            .toFlowable(BackpressureStrategy.DROP)
            .switchMap {
                GetTimeThisDay(eventRepository).execute(it)
                    .subscribeOn(Schedulers.computation())
            }
            .doOnNext { _timeToday.postValue(it) }
            .bind()
    }
}
