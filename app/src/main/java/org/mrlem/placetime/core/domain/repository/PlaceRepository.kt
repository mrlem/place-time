package org.mrlem.placetime.core.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Place

interface PlaceRepository {

    fun getAll(): Flowable<List<Place>>
    fun insertAll(vararg places: Place): Completable
}
