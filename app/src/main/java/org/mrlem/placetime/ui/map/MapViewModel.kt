package org.mrlem.placetime.ui.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import org.mrlem.placetime.PlaceTimeApplication.Companion.placeRepository
import org.mrlem.placetime.common.BaseViewModel
import org.mrlem.placetime.core.domain.model.Place
import timber.log.Timber

class MapViewModel : BaseViewModel() {

    private val _places = MutableLiveData(emptyList<Place>())
    val places get() = _places

    init {
        placeRepository
            .getAll()
            .doOnNext { Timber.d("places: ${it.size}") }
            .doOnNext { _places.postValue(it) }
            .bind()
    }

    fun createPlace(location: LatLng) {
        placeRepository
            .insert(Place("new place", location.latitude, location.longitude, 100f))
            .doOnSubscribe { Timber.d("creating") }
            .doOnComplete { Timber.i("created") }
            .bind()
        }
}
