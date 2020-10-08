package org.mrlem.placetime.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Place(
    val label: String,
    val latitude: Double,
    val longitude: Double
) {

    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}
