package org.mrlem.placetime.ui.map

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.mrlem.placetime.core.model.Place

class MapAdapter(private val map: GoogleMap, private val listener: MapListener) {

    private var bounds: LatLngBounds? = null
    private var places = emptyList<Place>()
    private val markers = mutableMapOf<Int, Marker>()
    private var initDone = false

    init {
        map.setOnMapLongClickListener { listener.onMapLongClick(it) }
    }

    fun updatePlaces(newPlaces: List<Place>) {
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
                    markers.remove(place.uid)
                }
            })

        bounds = LatLngBounds.builder()
            .takeIf { markers.isNotEmpty() }
            ?.apply { markers.values.forEach { marker -> include(marker.position) } }
            ?.build()
        places = newPlaces
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

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private class DiffPlaces(private val oldPlaces: List<Place>, private val newPlaces: List<Place>) {

        fun diff(callback: Callback) {
            val result = calculate()
            update(result, callback)
        }

        private fun calculate(): DiffUtil.DiffResult {
            return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    oldPlaces[oldItemPosition].uid == newPlaces[newItemPosition].uid

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    true // not interested

                override fun getOldListSize() = oldPlaces.size

                override fun getNewListSize() = newPlaces.size
            }, false)
        }

        private fun update(result: DiffUtil.DiffResult, callback: Callback) {
            result.dispatchUpdatesTo(object : ListUpdateCallback {
                override fun onInserted(position: Int, count: Int) {
                    (0 until count).forEach { i ->
                        callback.onPlaceAdded(newPlaces[position + i])
                    }
                }

                override fun onRemoved(position: Int, count: Int) {
                    (0 until count).forEach { i ->
                        callback.onPlaceRemoved(oldPlaces[position + i])
                    }
                }

                override fun onChanged(position: Int, count: Int, payload: Any?) {}

                override fun onMoved(fromPosition: Int, toPosition: Int) {}
            })
        }

        interface Callback {
            fun onPlaceAdded(place: Place)
            fun onPlaceRemoved(place: Place)
        }
    }
}
