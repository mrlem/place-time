package org.mrlem.placetime

import android.app.Application
import androidx.room.Room
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.mrlem.placetime.core.database.AppDatabase
import org.mrlem.placetime.core.model.Place

class PlaceTimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        // database setup & init
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "placetime-database").build()
        GlobalScope.launch(Dispatchers.IO) {
            createSampleData()
        }

        // geofencing client setup
        geofencingClient = LocationServices.getGeofencingClient(this)

        // TODO - add a service that selects places, and registers / unregisters them with the geofences client
    }

    private suspend fun createSampleData() {
        db.clearAllTables()
        db.placeDao().insertAll(
            Place("Travail", 48.1022243,-1.6747745, 100f),
            Place("Maison", 48.045511,-1.5142382, 100f)
        )
    }

    companion object {
        lateinit var instance: PlaceTimeApplication
        lateinit var db: AppDatabase
        lateinit var geofencingClient: GeofencingClient
    }
}
