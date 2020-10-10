package org.mrlem.placetime.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun list(): Flowable<List<Place>>

    @Insert
    fun insert(places: Place): Completable

    @Delete
    fun delete(place: Place): Completable
}
