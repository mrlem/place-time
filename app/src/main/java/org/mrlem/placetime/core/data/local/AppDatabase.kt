package org.mrlem.placetime.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.Place

@Database(entities = [Place::class, Event::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun eventDao(): EventDao
}
