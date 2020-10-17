package org.mrlem.placetime.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence.*
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import io.reactivex.disposables.CompositeDisposable
import org.mrlem.placetime.PlaceTimeApplication.Companion.eventRepository
import org.mrlem.placetime.common.addTo
import org.mrlem.placetime.core.domain.model.Event
import timber.log.Timber

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private val disposeOnDestroy = CompositeDisposable()

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            Timber.w("geofence receiver error: %s", errorMessage)
            return
        }

        val geofences = geofencingEvent.triggeringGeofences
        val type = geofencingEvent.geofenceTransition.geofenceTransitionToEventType()
        if (type == null) {
            Timber.w("geofence receiver unknown event type: %s", geofencingEvent.geofenceTransition)
            return
        }

        geofences.forEach { geofence ->
            eventRepository.insert(Event(placeUid = geofence.requestId.toInt(), type = type))
                .doOnError { Timber.e(it, "geofence event could not be written") }
                .subscribe()
                .addTo(disposeOnDestroy)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun Int.geofenceTransitionToEventType() = when (this) {
        GEOFENCE_TRANSITION_ENTER -> Event.Type.ENTER
        GEOFENCE_TRANSITION_EXIT -> Event.Type.EXIT
        else -> null
    }
}
