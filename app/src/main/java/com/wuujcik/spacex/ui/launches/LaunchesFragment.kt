package com.wuujcik.spacex.ui.launches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wuujcik.spacex.databinding.FragmentLaunchesBinding
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.wuujcik.spacex.persistence.launch.Launch
import com.wuujcik.spacex.ui.filterDialog.FilterDialog

class LaunchesFragment : Fragment() {

    private val launchesViewModel by viewModels<LaunchesViewModel>()

    private lateinit var binding: FragmentLaunchesBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return FragmentLaunchesBinding.inflate(inflater, container, false)
                .also {
                    binding = it
                }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LaunchesAdapter()

        with(binding) {
            launchesRecyclerView.adapter = adapter
            launchesRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter.onItemClicked = { launch ->
                val number = launch.flight_number // for the toolbar title
                if (number != null) {
                    findNavController().navigate(
                        LaunchesFragmentDirections.actionNavLaunchesToLaunchDetailsFragment(launch, number)
                    )
                }
            }
            filter.setOnClickListener {
                // TODO implement filtering
                val dialog = FilterDialog()
                dialog.show(parentFragmentManager, FilterDialog.TAG)
            }

            launchesViewModel.launches.observe(viewLifecycleOwner, { list: PagedList<Launch>? ->
                (adapter as? LaunchesAdapter)?.submitList(list)
            })
        }
    }

    companion object {
        const val TAG = "LaunchesFragment"
    }
}