package com.wuujcik.spacex.ui.launches

import android.os.Bundle
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
import com.wuujcik.spacex.ui.filterDialog.ApplyFilterDialogDelegate
import com.wuujcik.spacex.ui.filterDialog.FilterDialog
import java.util.*

class LaunchesFragment : Fragment(), ApplyFilterDialogDelegate {

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
                        LaunchesFragmentDirections.actionNavLaunchesToLaunchDetailsFragment(
                            launch,
                            number
                        )
                    )
                }
            }
            observeLaunches()
        }
       binding.filter.setOnClickListener {
            val dialog = FilterDialog()
            dialog.setTargetFragment(this, 0)
            dialog.show(parentFragmentManager, FilterDialog.TAG)
        }
    }

    override fun onFiltersApplied(startDate: Date, endDate: Date) {
        removeLaunchesObservers()
        launchesViewModel.applyFilter(startDate, endDate)
        observeLaunches()
    }

    private fun observeLaunches() {
        launchesViewModel.launches.observe(viewLifecycleOwner, { list: PagedList<Launch>? ->
            (binding.launchesRecyclerView.adapter as? LaunchesAdapter)?.submitList(list)
        })
    }

    private fun removeLaunchesObservers() {
        launchesViewModel.launches.removeObservers(viewLifecycleOwner)
    }
}
