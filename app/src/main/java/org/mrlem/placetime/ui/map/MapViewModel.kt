package org.mrlem.placetime.ui.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.snakydesign.livedataextensions.map
import org.mrlem.placetime.PlaceTimeApplication.Companion.placeRepository
import org.mrlem.placetime.common.BaseViewModel
import org.mrlem.placetime.core.domain.model.Place
import timber.log.Timber

class MapViewModel : BaseViewModel() {

    private val _places = MutableLiveData<List<Place>?>()
    val places get() = _places

    private val _selection = MutableLiveData<Place?>()
    val placeName get() = _selection.map { it?.label }
    val placePanelVisible get() = _selection.map { it != null }
    val selectionLocation get() = _selection.map { it?.run { LatLng(latitude, longitude) } }

    private val _hintShown = MutableLiveData(false)
    val hintShown get() = _hintShown

    init {
        val places = placeRepository
            .getAll()

        // places to show
        places
            .doOnNext { Timber.d("places: ${it.size}") }
            .doOnNext { _places.postValue(it) }
            .bind()

        // hint
        places
            .take(1)
            .filter { it.count() == 0 }
            .doOnNext { _hintShown.value = true }
            .bind()
    }

    fun createPlace(location: LatLng) {
        placeRepository
            .insert(Place("new place", location.latitude, location.longitude, 100f))
            .doOnSubscribe { Timber.d("creating") }
            .doOnComplete { Timber.i("created") }
            .bind()
        }

    fun select(place: Place) {
        _selection.value = place
    }

    fun deselect() {
        if (_selection.value == null) {
            _hintShown.value = true
        } else {
            _selection.value = null
        }
    }

    fun delete() {
        _selection.value?.let {
            placeRepository.delete(it)
                .doOnSubscribe { Timber.d("deleting") }
                .doOnComplete { Timber.i("deleted") }
                .bind()
        }
        _selection.value = null
    }
}
