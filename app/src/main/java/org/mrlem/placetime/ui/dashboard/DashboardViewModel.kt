package org.mrlem.placetime.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.mrlem.placetime.common.BaseViewModel

class DashboardViewModel : BaseViewModel() {

    private val _text = MutableLiveData("This is dashboard Fragment")
    val text = _text as LiveData<String>
}
