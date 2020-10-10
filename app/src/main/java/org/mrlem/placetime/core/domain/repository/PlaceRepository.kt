package org.mrlem.placetime.core.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Place

interface PlaceRepository {

    fun getAll(): Flowable<List<Place>>
    fun insert(place: Place): Completable
    fun delete(place: Place): Completable
    fun update(place: Place): Completable
}
