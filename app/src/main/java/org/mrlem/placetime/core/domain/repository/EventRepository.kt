package org.mrlem.placetime.core.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.EventAndPlace

interface EventRepository {

    fun list(): Flowable<List<EventAndPlace>>
    fun insert(event: Event): Completable
}
