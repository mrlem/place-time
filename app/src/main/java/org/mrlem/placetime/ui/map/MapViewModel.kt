package org.mrlem.placetime.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _text = MutableLiveData("This is map Fragment")
    val text: LiveData<String> = _text
}
