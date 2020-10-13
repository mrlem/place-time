package org.mrlem.placetime

import android.app.Application
import androidx.room.Room
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import org.mrlem.placetime.core.data.local.AppDatabase
import org.mrlem.placetime.core.data.remote.GeofenceAPI
import org.mrlem.placetime.core.data.repository.EventRepositoryImpl
import org.mrlem.placetime.core.data.repository.PlaceRepositoryImpl
import org.mrlem.placetime.core.domain.repository.EventRepository
import org.mrlem.placetime.core.domain.repository.PlaceRepository
import timber.log.Timber
import timber.log.Timber.DebugTree

class PlaceTimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        // database setup & init
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "placetime-database").build()

        // geofencing client setup
        geofencingClient = LocationServices.getGeofencingClient(this)
    }

    companion object {
        lateinit var instance: PlaceTimeApplication

        lateinit var db: AppDatabase
        lateinit var geofencingClient: GeofencingClient

        val geofenceAPI by lazy { GeofenceAPI(instance, geofencingClient) }
        val placeRepository: PlaceRepository by lazy { PlaceRepositoryImpl(db.placeDao(), geofenceAPI) }
        val eventRepository: EventRepository by lazy { EventRepositoryImpl(db.eventDao()) }
    }
}
