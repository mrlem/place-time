package org.mrlem.placetime.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData("This is dashboard Fragment")
    val text = _text as LiveData<String>
}
