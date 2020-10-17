package org.mrlem.placetime.core.data.remote

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import io.reactivex.Completable
import org.mrlem.placetime.core.GeofenceBroadcastReceiver
import org.mrlem.placetime.core.domain.model.Place
import java.util.concurrent.TimeUnit

class GeofenceAPI(
    context: Context,
    private val geofencingClient: GeofencingClient
) {

    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun createGeofence(place: Place): Completable = Completable.create { emitter ->
        // create geofence request
        val geofence = Geofence.Builder()
            .setRequestId(place.geofenceId)
            .setCircularRegion(
                place.latitude,
                place.longitude,
                place.radius
            )
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .setNotificationResponsiveness(TimeUnit.MINUTES.toMillis(3).toInt())
            .setExpirationDuration(TimeUnit.DAYS.toMillis(45))
            .build()
        val request = getGeofencingRequest(listOf(geofence))

        // send request
        geofencingClient.addGeofences(request, pendingIntent)?.run {
            addOnSuccessListener { emitter.onComplete() }
            addOnFailureListener { emitter.onError(it) }
        }
    }

    fun deleteGeofence(place: Place) = Completable.create { emitter ->
        // send request
        geofencingClient.removeGeofences(listOf(place.geofenceId))?.run {
            addOnSuccessListener { emitter.onComplete() }
            addOnFailureListener { emitter.onError(it) }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun getGeofencingRequest(geofences: List<Geofence>) = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(geofences)
            .build()
}
