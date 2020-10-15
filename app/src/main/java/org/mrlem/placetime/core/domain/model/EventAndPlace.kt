package org.mrlem.placetime.core.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class EventAndPlace(
    @Embedded
    val event: Event,
    @Relation(
        parentColumn = "placeUid",
        entityColumn = "uid"
    )
    val place: Place?
)
