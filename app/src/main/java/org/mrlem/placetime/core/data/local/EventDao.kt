package org.mrlem.placetime.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.EventAndPlace

@Dao
interface EventDao {
    @Transaction
    @Query("SELECT * FROM event")
    fun list(): Flowable<List<EventAndPlace>>

    @Insert
    fun insert(events: Event): Completable
}
