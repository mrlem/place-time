package org.mrlem.placetime.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    private val disposeOnClear = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        disposeOnClear.clear()
    }

    fun <T> Observable<T>.bind() = subscribe(
        { Unit },
        { Timber.e(it, "viewmodel observable failed") }
    )
        .addTo(disposeOnClear)

    fun <T> Flowable<T>.bind() = subscribe(
        { Unit },
        { Timber.e(it, "viewmodel flowable failed") }
    )
        .addTo(disposeOnClear)

    fun Completable.bind() = subscribe(
        { Unit },
        { Timber.e(it, "viewmodel completable failed") }
    )
        .addTo(disposeOnClear)
}
