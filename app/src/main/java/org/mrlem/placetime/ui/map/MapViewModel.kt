package org.mrlem.placetime.ui.map

import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.snakydesign.livedataextensions.map
import org.mrlem.placetime.PlaceTimeApplication.Companion.placeRepository
import org.mrlem.placetime.common.BaseViewModel
import org.mrlem.placetime.core.domain.model.Place
import timber.log.Timber

// TODO - error reporting

class MapViewModel : BaseViewModel() {

    private val _places = MutableLiveData<List<Place>?>()
    val places = _places as LiveData<List<Place>?>

    private val _selection = MutableLiveData<Place?>()
    val placeName = _selection.map { it?.label }
    val placePanelVisible = _selection.map { it != null }
    val selectionLocation = _selection.map { it?.run { LatLng(latitude, longitude) } }

    private val _hintShown = MutableLiveData(false)
    val hintShown = _hintShown as LiveData<Boolean>

    init {
        val places = placeRepository
            .list()

        // places to show
        places
            .doOnNext { Timber.d("places: ${it.size}") }
            .doOnNext { _places.postValue(it) }
            .bind()

        // hint
        places
            .take(1)
            .filter { it.count() == 0 }
            .doOnNext { _hintShown.postValue(true) }
            .bind()
    }

    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun createPlace(location: LatLng) {
        placeRepository
            .insert(Place("New place", location.latitude, location.longitude, 100f))
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
        val place = _selection.value ?: return

        placeRepository.delete(place)
            .doOnSubscribe { Timber.d("deleting") }
            .doOnComplete { Timber.i("deleted") }
            .doOnComplete { _selection.postValue(null) }
            .doOnError { Timber.e(it, "failed to delete") }
            .bind()
    }

    fun updateName(name: String) {
        val place = _selection.value
            ?.takeIf { it.label != name }
            ?: return

        place.label = name
        placeRepository.update(place)
            .doOnSubscribe { Timber.d("updating name") }
            .doOnComplete { Timber.i("updated name") }
            .bind()
    }

    fun move(place: Place, location: LatLng) {
        place.latitude = location.latitude
        place.longitude = location.longitude
        placeRepository.update(place)
            .doOnSubscribe { Timber.d("updating location") }
            .doOnComplete { Timber.i("updated location") }
            .bind()
    }
}
