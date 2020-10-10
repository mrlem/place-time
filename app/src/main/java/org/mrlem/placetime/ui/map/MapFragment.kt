package org.mrlem.placetime.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.mrlem.placetime.R

class MapFragment : Fragment(), OnMapReadyCallback {

    private val viewModel by lazy { ViewModelProvider(this).get(MapViewModel::class.java) }
    private lateinit var mapAdapter: MapAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
   }

    override fun onMapReady(googleMap: GoogleMap) {
        mapAdapter = MapAdapter(googleMap)

        // observations
        viewModel.places
            .observe(viewLifecycleOwner) { places ->
                mapAdapter.updatePlaces(places)
                mapAdapter.center()
            }
    }
}
