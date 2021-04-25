package com.wuujcik.spacex.ui.launchDetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.spacex.persistence.Rocket
import com.wuujcik.spacex.providers.LaunchProvider

class LaunchDetailsViewModel : ViewModel() {

    private val provider: LaunchProvider by lazy {
        LaunchProvider()
    }

    val rocket: LiveData<Rocket?> = provider.rocket

    fun getRocket(rocketId: String?) {
        provider.getRocket(viewModelScope, rocketId)
    }

    fun sendIntentForUrl(url: String?, context: Context?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context?.let {
            startActivity(context, intent, null)
        }
    }
}
