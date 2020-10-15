package org.mrlem.placetime.core.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.EventAndPlace

@Dao
interface EventDao {
    @Transaction
    @Query("SELECT * FROM event")
    fun list(): Flowable<List<EventAndPlace>>

    @Query("SELECT * FROM event WHERE eventPlaceUid = :placeUid AND time >= :timestamp")
    fun listSince(placeUid: Int, timestamp: Long): Flowable<List<Event>>

    @Insert
    fun insert(events: Event): Completable

    @Query("DELETE FROM event WHERE eventPlaceUid = :uid")
    fun deleteForPlace(uid: Int): Completable
}
