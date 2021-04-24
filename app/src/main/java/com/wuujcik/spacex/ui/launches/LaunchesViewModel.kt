package com.wuujcik.spacex.ui.launches

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.providers.LaunchProvider

class LaunchesViewModel : ViewModel() {

    private val provider: LaunchProvider by lazy {
        LaunchProvider()
    }

    val upcomingLaunches: LiveData<List<Launch?>?> = provider.upcomingLaunches

    fun requestUpcomingLaunches() {
        provider.getUpcomingLaunches(viewModelScope)
        Log.e("TAG", "Requesting upcoming launches")
    }
}
