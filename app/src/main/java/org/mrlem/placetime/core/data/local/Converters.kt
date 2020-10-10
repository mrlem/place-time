package org.mrlem.placetime.core.data.local

import androidx.room.TypeConverter
import org.mrlem.placetime.core.domain.model.Event
import org.mrlem.placetime.core.domain.model.GeofenceStatus

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
