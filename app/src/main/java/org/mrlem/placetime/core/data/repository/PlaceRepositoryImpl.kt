package org.mrlem.placetime.core.data.repository

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.mrlem.placetime.core.data.local.PlaceDao
import org.mrlem.placetime.core.domain.model.Place
import org.mrlem.placetime.core.domain.repository.PlaceRepository

class PlaceRepositoryImpl(private val placeDao: PlaceDao) : PlaceRepository {

    override fun getAll(): Flowable<List<Place>> =
        placeDao
            .list()
            .subscribeOn(Schedulers.io())

    override fun insert(place: Place) =
        placeDao
            .insert(place)
            .subscribeOn(Schedulers.io())

    override fun delete(place: Place) =
        placeDao
            .delete(place)
            .subscribeOn(Schedulers.io())

    override fun update(place: Place) =
        placeDao
            .update(place)
            .subscribeOn(Schedulers.io())
}
