package com.wuujcik.spacex.ui.launchDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.wuujcik.spacex.R
import com.wuujcik.spacex.databinding.FragmentLaunchDetailsBinding
import com.wuujcik.spacex.persistence.launch.Launch
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
        launchDetailsViewModel.getRocket(launch?.rocketId)

        with(binding) {

            flightName.text = launch?.name
            val dateUnix = launch?.date_unix
            if (dateUnix != null) {
                context?.let {
                    val datetime =formatDateTime(it, Date(dateUnix * 1000))
                    date.text = getString(R.string.launch_when, datetime)
                }
            }
            image.load(launch?.links?.patch?.small)

            if (launch?.upcoming == true) {
                details.text = launch?.details ?: getString(R.string.future_launch)
            } else {
                details.text = launch?.details ?: getString(R.string.no_data)
            }

            if(launch?.links?.article.isNullOrEmpty()){
                readMore.visibility = View.INVISIBLE
            } else {
                readMore.visibility = View.VISIBLE
                readMore.setOnClickListener {
                    launchDetailsViewModel.sendIntentForUrl(launch?.links?.article, context)
                }
            }

            launchDetailsViewModel.rocket.observe(viewLifecycleOwner, { rocket ->
                rocketNameData.text = rocket?.name
                rocketTypeData.text = rocket?.type
                rocketDescriptionData.text = rocket?.description
                rocketImg.load(rocket?.flickr_images?.get(0))
            })
        }
    }

    companion object {
        const val TAG = "CompanyInfoFragment"
    }
}