package org.mrlem.placetime.ui.map

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import org.mrlem.placetime.R
import org.mrlem.placetime.core.domain.model.Place

// TODO - enable GPS
class MapAdapter(context: Context, private val map: GoogleMap, private val listener: MapListener) {

    private var bounds: LatLngBounds? = null
    private var places = emptyList<Place>()
    private val markers = mutableMapOf<Int, Marker>()
    private val circles = mutableMapOf<Int, Circle>()
    private val circleFillColor = ContextCompat.getColor(context, R.color.colorRange)
    private val circleStrokeColor = ContextCompat.getColor(context, R.color.colorPrimary)

    init {
        // setup map events
        map.setOnMapLongClickListener { listener.onPlaceCreateRequested(it) }
        map.setOnMapClickListener { listener.onPlaceDeselectRequested() }
        map.setOnMarkerClickListener { marker ->
            marker.place?.let {
                listener.onPlaceSelectRequested(it)
                true
            } ?: false
        }
        map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(marker: Marker) {
                marker.place?.let { listener.onPlaceMoveRequested(it, marker.position) }
            }

            override fun onMarkerDragStart(marker: Marker) {}
            override fun onMarkerDrag(marker: Marker) {}
        })
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
                        .draggable(true)
                    map.addMarker(markerOptions)
                        .also { markers[place.uid] = it }

                    // create circle
                    val circleOptions = CircleOptions()
                        .center(location)
                        .radius(place.radius.toDouble())
                        .strokeWidth(1f)
                        .strokeColor(circleStrokeColor)
                        .fillColor(circleFillColor)
                    map.addCircle(circleOptions)
                        .also { circles[place.uid] = it }
                }

                override fun onPlaceRemoved(place: Place) {
                    // remove marker
                    markers.remove(place.uid)
                        ?.remove()

                    // remove circle
                    circles.remove(place.uid)
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

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private val Marker.place: Place? get() {
        val placeId = markers.entries.firstOrNull { it.value == this }?.key
        return places.firstOrNull { it.uid == placeId }
    }
}
