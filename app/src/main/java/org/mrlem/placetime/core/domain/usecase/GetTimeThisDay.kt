package org.mrlem.placetime.core.domain.usecase

import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Place
import org.mrlem.placetime.core.domain.repository.EventRepository
import java.util.*

class GetTimeThisDay(eventRepository: EventRepository) : GetTimeSince(eventRepository) {

    fun execute(place: Place): Flowable<String> = execute(place, todaysTimestamp())

    private fun todaysTimestamp(): Long = GregorianCalendar()
        .apply {
            this[Calendar.HOUR_OF_DAY] = 0
            this[Calendar.MINUTE] = 0
            this[Calendar.SECOND] = 0
            this[Calendar.MILLISECOND] = 0
        }.timeInMillis
}
