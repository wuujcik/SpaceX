package com.wuujcik.spacex.ui.launchDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.providers.LaunchProvider

class LaunchDetailsViewModel : ViewModel() {

    private val provider: LaunchProvider by lazy {
        LaunchProvider()
    }

//    val launchDetails: LiveData<Launch?> = provider.launchDetails

    fun requestLaunchDetails() {
//        provider.getLaunchDetails(viewModelScope)
    }
}
