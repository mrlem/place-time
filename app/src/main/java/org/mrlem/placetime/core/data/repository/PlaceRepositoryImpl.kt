package org.mrlem.placetime.core.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.mrlem.placetime.core.data.local.EventDao
import org.mrlem.placetime.core.data.local.PlaceDao
import org.mrlem.placetime.core.data.remote.GeofenceAPI
import org.mrlem.placetime.core.domain.model.GeofenceStatus
import org.mrlem.placetime.core.domain.model.Place
import org.mrlem.placetime.core.domain.repository.PlaceRepository

class PlaceRepositoryImpl(
    private val placeDao: PlaceDao,
    private val eventDao: EventDao,
    private val geofenceAPI: GeofenceAPI
) : PlaceRepository {

    override fun list(): Flowable<List<Place>> =
        placeDao
            .list()
            .subscribeOn(Schedulers.io())

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun insert(place: Place): Completable =
        placeDao.update(place.apply { status = GeofenceStatus.CREATING })
            .subscribeOn(Schedulers.io())
            .andThen(
                geofenceAPI.createGeofence(place)
                    .subscribeOn(Schedulers.io())
            )
            .andThen(
                placeDao.insert(place.apply { status = GeofenceStatus.CREATED })
                    .subscribeOn(Schedulers.io())
            )

    override fun delete(place: Place): Completable =
        placeDao.update(place.apply { status = GeofenceStatus.DELETING })
            .subscribeOn(Schedulers.io())
            .andThen(
                geofenceAPI.deleteGeofence(place)
                    .subscribeOn(Schedulers.io())
            )
            .andThen(
                eventDao.deleteForPlace(place.uid)
                    .subscribeOn(Schedulers.io())
            )
            .andThen(
                placeDao.delete(place)
                    .subscribeOn(Schedulers.io())
            )

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun update(place: Place): Completable =
        // TODO - don't do this on name change (only update the dao)
        placeDao.update(place.apply { status = GeofenceStatus.DELETING })
            .subscribeOn(Schedulers.io())
            .andThen(
                geofenceAPI.deleteGeofence(place)
                    .subscribeOn(Schedulers.io())
            )
            .andThen(
                placeDao.update(place.apply { status = GeofenceStatus.CREATING })
                    .subscribeOn(Schedulers.io())
            )
            .andThen(
                geofenceAPI.createGeofence(place)
                    .subscribeOn(Schedulers.io())
            )
            .andThen(
                placeDao.update(place.apply { status = GeofenceStatus.CREATED })
                    .subscribeOn(Schedulers.io())
            )
}
