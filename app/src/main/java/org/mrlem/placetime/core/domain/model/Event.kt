package org.mrlem.placetime.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    val placeUid: Int,
    val type: Type,
    val time: Long = System.currentTimeMillis()
) {

    @PrimaryKey(autoGenerate = true) var uid: Long = 0

    enum class Type {
        ENTER,
        DWELL,
        EXIT
    }
}
