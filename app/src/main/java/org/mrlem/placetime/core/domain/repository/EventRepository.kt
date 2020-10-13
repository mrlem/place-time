package org.mrlem.placetime.core.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event

interface EventRepository {

    fun list(): Flowable<List<Event>>
    fun insert(event: Event): Completable
}
