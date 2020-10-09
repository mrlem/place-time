package org.mrlem.placetime.core.database

import androidx.room.TypeConverter
import org.mrlem.placetime.core.model.Event
import org.mrlem.placetime.core.model.GeofenceStatus

class Converters {
    @TypeConverter
    fun nameToStatus(name: String?): GeofenceStatus? = name?.let { GeofenceStatus.valueOf(it) }

    @TypeConverter
    fun statusToName(status: GeofenceStatus?): String? = status?.name

    @TypeConverter
    fun nameToEventType(name: String?): Event.Type? = name?.let { Event.Type.valueOf(it) }

    @TypeConverter
    fun eventTypeToName(eventType: Event.Type?): String? = eventType?.name
}
