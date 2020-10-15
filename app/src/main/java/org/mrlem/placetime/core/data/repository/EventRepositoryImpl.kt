package org.mrlem.placetime.core.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.mrlem.placetime.core.data.local.EventDao
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.EventAndPlace
import org.mrlem.placetime.core.domain.model.Place
import org.mrlem.placetime.core.domain.repository.EventRepository

class EventRepositoryImpl(
    private val eventDao: EventDao,
) : EventRepository {

    override fun list(): Flowable<List<EventAndPlace>> =
        eventDao
            .list()
            .subscribeOn(Schedulers.io())

    override fun listSince(place: Place, timestamp: Long): Flowable<List<Event>> =
        eventDao
            .listSince(place.uid, timestamp)
            .subscribeOn(Schedulers.io())

    override fun insert(event: Event): Completable =
        eventDao
            .insert(event)
            .subscribeOn(Schedulers.io())
}
