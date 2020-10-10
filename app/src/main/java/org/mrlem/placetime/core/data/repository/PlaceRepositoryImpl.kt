package org.mrlem.placetime.core.data.repository

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.mrlem.placetime.core.data.local.PlaceDao
import org.mrlem.placetime.core.domain.model.Place
import org.mrlem.placetime.core.domain.repository.PlaceRepository

class PlaceRepositoryImpl(private val placeDao: PlaceDao) : PlaceRepository {

    override fun getAll(): Flowable<List<Place>> =
        placeDao
            .getAll()
            .subscribeOn(Schedulers.io())

    override fun insertAll(vararg places: Place) =
        placeDao
            .insertAll(*places)
            .subscribeOn(Schedulers.io())
}
