package org.mrlem.placetime.core.domain.usecase

import io.reactivex.Flowable
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.Place
import org.mrlem.placetime.core.domain.repository.EventRepository
import java.util.*
import java.util.concurrent.TimeUnit

abstract class GetTimeSince(private val eventRepository: EventRepository) {

    protected fun execute(place: Place, timestamp: Long): Flowable<String> = Flowable.combineLatest(
        eventRepository.listSince(place, timestamp),
        Flowable.interval(1L, TimeUnit.SECONDS)
    ) { events, _ -> events }
        .map { events ->
            var total = 0L
            var enter: Long? = timestamp

            for (event in events) {
                when (event.type) {
                    Event.Type.ENTER -> enter = event.time
                    Event.Type.EXIT -> {
                        total += event.time - enter!!
                        enter = null
                    }
                    else -> Unit
                }
            }

            if (enter != null) {
                total += Date().time - enter
            }
            total
        }
        .map { it.millisToDuration() }

    private fun Long.millisToDuration(): String {
        var remainder = this
        val hours = TimeUnit.MILLISECONDS.toHours(remainder)
            .also { remainder -= TimeUnit.HOURS.toMillis(it) }
        val minutes = TimeUnit.MILLISECONDS.toMinutes(remainder)
            .also { remainder -= TimeUnit.MINUTES.toMillis(it) }
        val seconds = TimeUnit.MILLISECONDS.toSeconds(remainder)

        return "${hours.toTwoDigits()}:${minutes.toTwoDigits()}:${seconds.toTwoDigits()}"
    }

    private fun Long.toTwoDigits() = this.toString().padStart(2, '0')
}
