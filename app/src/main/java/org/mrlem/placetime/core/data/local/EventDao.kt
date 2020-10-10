package org.mrlem.placetime.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll(): Flowable<List<Event>>

    @Insert
    fun insertAll(vararg events: Event): Completable

    @Delete
    fun delete(event: Event): Completable
}
