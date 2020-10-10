package org.mrlem.placetime.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.snakydesign.livedataextensions.*
import kotlinx.android.synthetic.main.fragment_map.*
import org.mrlem.placetime.R
import org.mrlem.placetime.common.setOnDoneEditingListener
import org.mrlem.placetime.core.domain.model.Place

class MapFragment : Fragment(), OnMapReadyCallback, MapListener {

    private val viewModel by lazy { ViewModelProvider(this).get(MapViewModel::class.java) }
    private var mapAdapter: MapAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapAdapter = null
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // events
        placeDelete.setOnClickListener { viewModel.delete() }
        placeName.setOnDoneEditingListener { viewModel.updateName(it) }

        // observations
        viewModel.places
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { places ->
                places?.let { mapAdapter?.updatePlaces(it) }
            }

        viewModel.placeName
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, placeName::setText)

        viewModel.placePanelVisible
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { placePanel.isVisible = it }

        viewModel.selectionLocation
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { it?.run { mapAdapter?.center(it) } }

        viewModel.hintShown
            .filter { shown -> shown == true }
            .observe(viewLifecycleOwner) { hint() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapAdapter = MapAdapter(googleMap, this)
        viewModel.places.value?.let { places -> mapAdapter?.updatePlaces(places) }
        googleMap.setOnMapLoadedCallback {
            mapAdapter?.center(true)
        }
    }

    override fun onPlaceCreateRequested(location: LatLng) {
        viewModel.createPlace(location)
    }

    override fun onPlaceDeselect(location: LatLng) {
        if (placeName.hasFocus()) {
            placeName.clearFocus()
        } else {
            viewModel.deselect()
        }
    }

    override fun onPlaceSelectRequested(place: Place) {
        viewModel.select(place)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun hint() {
        Toast
            .makeText(requireContext(), R.string.map_create_hint, Toast.LENGTH_SHORT)
            .show()
    }
}
