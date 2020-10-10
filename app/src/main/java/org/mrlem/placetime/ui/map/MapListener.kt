package org.mrlem.placetime.ui.map

import com.google.android.gms.maps.model.LatLng
import org.mrlem.placetime.core.domain.model.Place

interface MapListener {

    fun onMapLongClick(location: LatLng)
    fun onMapClick(location: LatLng)
}