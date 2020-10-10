package org.mrlem.placetime.ui.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import org.mrlem.placetime.core.model.Place

class MapAdapter(private val map: GoogleMap, private val listener: MapListener) {

    private var bounds: LatLngBounds? = null

    init {
        map.setOnMapLongClickListener { listener.onMapLongClick(it) }
    }

    fun updatePlaces(places: List<Place>) {
        val boundsBuider = LatLngBounds.builder()
        map.clear()

        // TODO - only diff
        places.forEach { place ->
            val location = LatLng(place.latitude, place.longitude)
            boundsBuider.include(location)
            map.addMarker(MarkerOptions().position(location).title(place.label))
        }

        bounds = boundsBuider
            .takeIf { places.isNotEmpty() }
            ?.build()
    }

    fun center() {
        bounds?.let {
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(it, 150))
        }
    }
}
