package org.mrlem.placetime.ui.map

import com.google.android.gms.maps.model.LatLng
import org.mrlem.placetime.core.model.Place

interface MapListener {

    fun onMapLongClick(location: LatLng)
}