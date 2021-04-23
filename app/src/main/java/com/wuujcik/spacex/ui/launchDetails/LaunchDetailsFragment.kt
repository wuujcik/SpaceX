package com.wuujcik.spacex.ui.launchDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wuujcik.spacex.databinding.FragmentLaunchDetailsBinding

class LaunchDetailsFragment : Fragment() {

    private val launchDetailsViewModel by viewModels<LaunchDetailsViewModel>()

    private lateinit var binding: FragmentLaunchDetailsBinding


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

        launchDetailsViewModel.requestLaunchDetails()

        with(binding) {

//            launchDetailsViewModel.launchDetails.observe(viewLifecycleOwner, { info ->
//
//            })
        }

    }

    companion object {
        const val TAG = "CompanyInfoFragment"
    }
}