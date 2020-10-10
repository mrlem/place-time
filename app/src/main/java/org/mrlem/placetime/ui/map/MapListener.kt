package org.mrlem.placetime.ui.map

import com.google.android.gms.maps.model.LatLng
import org.mrlem.placetime.core.domain.model.Place

interface MapListener {

    fun onPlaceCreateRequested(location: LatLng)
    fun onPlaceDeselect()
    fun onPlaceSelectRequested(place: Place)
}
