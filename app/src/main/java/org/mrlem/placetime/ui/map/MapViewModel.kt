package org.mrlem.placetime.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.mrlem.placetime.PlaceTimeApplication
import org.mrlem.placetime.core.model.Place

class MapViewModel : ViewModel() {

    private val _places = MutableLiveData(emptyList<Place>())
    val places get() = _places

    init {
        viewModelScope.launch {
            PlaceTimeApplication.db.placeDao().getAll()
                .collect { _places.value = it }

        }
    }
}
