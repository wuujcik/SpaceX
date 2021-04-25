package com.wuujcik.spacex.ui.launchDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.wuujcik.spacex.databinding.FragmentLaunchDetailsBinding
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.utils.formatDateTime
import java.util.*


class LaunchDetailsFragment : Fragment() {

    private val launchDetailsViewModel by viewModels<LaunchDetailsViewModel>()

    private lateinit var binding: FragmentLaunchDetailsBinding
    private var launch: Launch? = null
    private val args: LaunchDetailsFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return FragmentLaunchDetailsBinding.inflate(inflater, container, false)
                .also {
                    binding = it
                }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch = args.data

        with(binding) {

            flightName.text = launch?.name
            val dateUnix = launch?.date_unix
            if (dateUnix != null) {
                date.text = context?.let { formatDateTime(it, Date(dateUnix * 1000)) }
            }

            val failuresArray = launch?.failures

            if (!failuresArray.isNullOrEmpty()) {
                failuresTitle.visibility = View.VISIBLE
                failuresLayout.visibility = View.VISIBLE
                val altitude = failuresArray[0]?.altitude ?: 0 // TODO: show more than 1
                failureTimeData.text = failuresArray[0]?.time.toString() // TODO: show more than 1
                failureAltitudeData.text = altitude.toString()
                failureReasonData.text = failuresArray[0]?.reason.toString() // TODO: show more than 1
            } else {
                failuresTitle.visibility = View.GONE
                failuresLayout.visibility = View.GONE
            }

            details.text = launch?.details

            readMore.setOnClickListener {
                launchDetailsViewModel.sendIntentForUrl(launch?.links?.article, context)
            }
        }

    }

    companion object {
        const val TAG = "CompanyInfoFragment"
    }
}