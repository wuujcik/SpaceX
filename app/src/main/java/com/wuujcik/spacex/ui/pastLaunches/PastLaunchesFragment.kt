package com.wuujcik.spacex.ui.pastLaunches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wuujcik.spacex.databinding.FragmentPastLaunchesBinding

class PastLaunchesFragment : Fragment() {

    private val pastLaunchesViewModel by viewModels<PastLaunchesViewModel>()

    private lateinit var binding: FragmentPastLaunchesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPastLaunchesBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pastLaunchesViewModel.requestPastLaunches()

        with(binding) {

            pastLaunchesViewModel.pastLaunches.observe(viewLifecycleOwner, { launches ->
//                binding.textGallery.text = launches?.name
                Log.e(TAG, "past launches: $launches")
            })
        }

    }

    companion object {
        const val TAG = "PastLaunchesFragment"
    }
}