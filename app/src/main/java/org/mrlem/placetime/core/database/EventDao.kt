package org.mrlem.placetime.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mrlem.placetime.core.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll(): Flow<List<Event>>

    @Insert
    suspend fun insertAll(vararg events: Event)

    @Delete
    suspend fun delete(event: Event)
}
