package org.mrlem.placetime.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event ORDER BY time ASC LIMIT 100")
    fun list(): Flowable<List<Event>>

    @Insert
    fun insert(events: Event): Completable
}
