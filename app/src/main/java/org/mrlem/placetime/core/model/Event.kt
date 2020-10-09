package org.mrlem.placetime.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val placeId: Int, // TODO - relation
    val type: Type
) {

    enum class Type {
        ENTER,
        DWELL,
        EXIT
    }
}
