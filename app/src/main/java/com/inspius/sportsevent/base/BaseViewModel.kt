package com.inspius.sportsevent.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _toastLiveData = MutableLiveData<String>()
    val toastLiveData: LiveData<String>
        get() = _toastLiveData
}