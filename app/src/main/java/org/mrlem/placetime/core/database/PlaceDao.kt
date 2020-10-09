package org.mrlem.placetime.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mrlem.placetime.core.model.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun getAll(): Flow<List<Place>>

    @Insert
    suspend fun insertAll(vararg places: Place)

    @Delete
    suspend fun delete(place: Place)
}
