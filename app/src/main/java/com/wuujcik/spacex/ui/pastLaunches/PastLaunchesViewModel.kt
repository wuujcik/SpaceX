package com.wuujcik.spacex.ui.pastLaunches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.providers.LaunchProvider

class PastLaunchesViewModel : ViewModel() {

    private val provider: LaunchProvider by lazy {
        LaunchProvider()
    }

    val pastLaunches: LiveData<List<Launch?>?> = provider.pastLaunches

    fun requestPastLaunches() {
        provider.getPastLaunches(viewModelScope)
        Log.e("TAG", "Requesting past launches")
    }
}