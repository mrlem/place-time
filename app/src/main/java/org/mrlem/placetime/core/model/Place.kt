package org.mrlem.placetime.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Place(
    var label: String,
    var latitude: Double,
    var longitude: Double,
    var radius: Float,
    var status: GeofenceStatus = GeofenceStatus.TO_BE_CREATED
) {

    @PrimaryKey(autoGenerate = true) var uid: Int = 0
    val geofenceId get() = uid.toString()
}
