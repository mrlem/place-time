package org.mrlem.placetime.core.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.EventAndPlace
import org.mrlem.placetime.core.domain.model.Place

@Dao
interface EventDao {
    @Transaction
    @Query("SELECT * FROM event")
    fun list(): Flowable<List<EventAndPlace>>

    @Insert
    fun insert(events: Event): Completable

    @Query("DELETE FROM event WHERE placeUid = :uid")
    fun deleteForPlace(uid: Int): Completable
}
