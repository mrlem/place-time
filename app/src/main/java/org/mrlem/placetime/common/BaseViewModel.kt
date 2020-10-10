package org.mrlem.placetime.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val disposeOnClear = CompositeDisposable()

    override fun onCleared() {
        disposeOnClear.clear()
    }
}
