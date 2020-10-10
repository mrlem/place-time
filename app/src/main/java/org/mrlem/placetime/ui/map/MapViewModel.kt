package org.mrlem.placetime.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.mrlem.placetime.PlaceTimeApplication.Companion.db
import org.mrlem.placetime.core.model.Place

class MapViewModel : ViewModel() {

    private val _places = MutableLiveData(emptyList<Place>())
    val places get() = _places

    init {
        viewModelScope.launch {
            db.placeDao()
                .getAll()
                .collect { _places.value = it }

        }
    }

    fun createPlace(location: LatLng) {
        viewModelScope.launch {
            db.placeDao()
                .insertAll(Place("New place", location.latitude, location.longitude, 100f))
        }
    }
}
