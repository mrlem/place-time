package org.mrlem.placetime.core.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.mrlem.placetime.core.domain.model.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun list(): Flowable<List<Place>>

    @Query("SELECT * FROM place INNER JOIN event ORDER BY time DESC LIMIT 1")
    fun current(): Observable<Place>

    @Insert
    fun insert(place: Place): Completable

    @Delete
    fun delete(place: Place): Completable

    @Update
    fun update(place: Place): Completable
}
