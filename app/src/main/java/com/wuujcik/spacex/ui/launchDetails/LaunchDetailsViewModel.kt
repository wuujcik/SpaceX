package com.wuujcik.spacex.ui.launchDetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel

class LaunchDetailsViewModel : ViewModel() {

    fun sendIntentForUrl(url: String?, context: Context?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context?.let {
            startActivity(context, intent, null)
        }
    }
}
