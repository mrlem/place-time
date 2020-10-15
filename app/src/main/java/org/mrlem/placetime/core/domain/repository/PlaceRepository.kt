package org.mrlem.placetime.core.domain.repository

import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.annotation.RequiresPermission
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.mrlem.placetime.core.domain.model.Place

interface PlaceRepository {

    fun list(): Flowable<List<Place>>
    fun current(): Observable<Place>
    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun insert(place: Place): Completable
    fun delete(place: Place): Completable
    fun update(place: Place): Completable
}
