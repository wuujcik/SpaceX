package com.wuujcik.spacex.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.providers.LaunchProvider

class LaunchesViewModel : ViewModel() {

    private val provider: LaunchProvider by lazy {
        LaunchProvider()
    }

    val launches: LiveData<List<Launch?>?> = provider.launches

    fun requestLaunches() {
        provider.getLaunches(viewModelScope)
    }

    fun filterLaunches() {
        provider.getFilteredLaunches(viewModelScope, "")
    }
}
