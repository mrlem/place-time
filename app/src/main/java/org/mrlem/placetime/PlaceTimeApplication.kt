package org.mrlem.placetime

import android.app.Application
import androidx.room.Room
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.mrlem.placetime.core.data.local.AppDatabase
import org.mrlem.placetime.core.data.repository.PlaceRepositoryImpl
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
        GlobalScope.launch(Dispatchers.IO) {
            db.clearAllTables()
        }

        // geofencing client setup
        geofencingClient = LocationServices.getGeofencingClient(this)

        // TODO - add a service that selects places, and registers / unregisters them with the geofences client
    }

    companion object {
        lateinit var instance: PlaceTimeApplication
        lateinit var db: AppDatabase
        lateinit var geofencingClient: GeofencingClient

        val placeRepository: PlaceRepository by lazy { PlaceRepositoryImpl(db.placeDao()) }
    }
}
