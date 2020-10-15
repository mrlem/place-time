package org.mrlem.placetime.core.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.EventAndPlace
import org.mrlem.placetime.core.domain.model.Place

interface EventRepository {

    fun list(): Flowable<List<EventAndPlace>>
    fun listSince(place: Place, timestamp: Long): Flowable<List<Event>>
    fun insert(event: Event): Completable
}
