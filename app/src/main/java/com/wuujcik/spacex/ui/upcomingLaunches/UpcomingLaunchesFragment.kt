package com.wuujcik.spacex.ui.upcomingLaunches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wuujcik.spacex.databinding.FragmentUpcomingLaunchesBinding

class UpcomingLaunchesFragment : Fragment() {

    private val upcomingLaunchesViewModel by viewModels<UpcomingLaunchesViewModel>()

    private lateinit var binding: FragmentUpcomingLaunchesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUpcomingLaunchesBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingLaunchesViewModel.requestUpcomingLaunches()
        val adapter = UpcomingLaunchesAdapter()

        with(binding) {
            upcomingLaunchesRecyclerView.adapter = adapter
            upcomingLaunchesRecyclerView.layoutManager = LinearLayoutManager(context)

            upcomingLaunchesViewModel.upcomingLaunches.observe(viewLifecycleOwner, { launches ->
                if (launches.isNullOrEmpty()) {
                    // TODO
//                    progress_bar_overlay?.visibility = View.VISIBLE
                } else {
//                    progress_bar_overlay?.visibility = View.GONE
                    adapter.submitList(launches)
                    adapter.onItemClicked = { launch ->
                        findNavController().navigate(
                            UpcomingLaunchesFragmentDirections.actionNavUpcomingLaunchesToLaunchDetailsFragment(launch)
                        )
                    }
                }
            })
        }

    }

    companion object {
        const val TAG = "UpcomingLaunchesFr"
    }
}