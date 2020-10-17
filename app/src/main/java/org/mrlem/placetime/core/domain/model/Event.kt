package org.mrlem.placetime.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @ColumnInfo(name="eventPlaceUid")
    val placeUid: Int,
    val type: Type,
    val time: Long = System.currentTimeMillis()
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="eventUid")
    var uid: Long = 0

    enum class Type {
        ENTER,
        EXIT
    }
}
