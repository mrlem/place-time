package org.mrlem.placetime.ui.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.mrlem.placetime.core.domain.model.Place

class MapAdapter(private val map: GoogleMap, private val listener: MapListener) {

    private var bounds: LatLngBounds? = null
    private var places = emptyList<Place>()
    private val markers = mutableMapOf<Int, Marker>()

    init {
        // setup map events
        map.setOnMapLongClickListener { listener.onPlaceCreateRequested(it) }
        map.setOnMapClickListener { listener.onPlaceDeselect(it) }
        map.setOnMarkerClickListener { marker ->
            // find corresponding place
            val placeId = markers.entries.firstOrNull { it.value == marker }?.key
            val place = places.firstOrNull { it.uid == placeId }

            place?.let {
                listener.onPlaceSelectRequested(it)
                true
            } ?: false
        }
    }

    fun updatePlaces(newPlaces: List<Place>) {
        // find which places were added / removed
        DiffPlaces(places, newPlaces)
            .diff(object : DiffPlaces.Callback {
                override fun onPlaceAdded(place: Place) {
                    val location = LatLng(place.latitude, place.longitude)

                    // create marker
                    val markerOptions = MarkerOptions()
                        .position(location)
                        .title(place.label)
                    map.addMarker(markerOptions)
                        .also { markers[place.uid] = it }
                }

                override fun onPlaceRemoved(place: Place) {
                    // remove marker
                    markers.remove(place.uid)
                        ?.remove()
                }
            })
        places = newPlaces

        bounds = LatLngBounds.builder()
            .takeIf { markers.isNotEmpty() }
            ?.apply { markers.values.forEach { marker -> include(marker.position) } }
            ?.build()
    }

    fun center(animated: Boolean) {
        bounds?.let {
            if (animated) {
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(it, 150))
            } else {
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(it, 150))
            }
        }
    }

    fun center(location: LatLng) {
        map.animateCamera(CameraUpdateFactory.newLatLng(location))
    }

}
