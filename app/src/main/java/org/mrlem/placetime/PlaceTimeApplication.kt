package org.mrlem.placetime

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.mrlem.placetime.data.model.AppDatabase
import org.mrlem.placetime.data.model.Place

class PlaceTimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "placetime-database").build()

        GlobalScope.launch(Dispatchers.IO) {
            createSampleData()
        }
    }

    private suspend fun createSampleData() {
        db.clearAllTables()
        db.placeDao().insertAll(
            Place("Travail", 48.1022243,-1.6747745),
            Place("Maison", 48.045511,-1.5142382)
        )
    }

    companion object {
        lateinit var db: AppDatabase
    }
}
