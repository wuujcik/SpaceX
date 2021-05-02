package com.wuujcik.spacex.ui.filterDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.wuujcik.spacex.databinding.DialogFilterBinding
import com.wuujcik.spacex.providers.LaunchProvider
import com.wuujcik.spacex.ui.launches.LaunchesFragment
import com.wuujcik.spacex.ui.launches.LaunchesViewModel
import com.wuujcik.spacex.utils.resizeDialogWindowToMatchScreen
import java.util.*


class FilterDialog : DialogFragment() {

    private val launchesViewModel by viewModels<LaunchesViewModel>()
    private lateinit var binding: DialogFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DialogFilterBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            launchesViewModel.getFirstLaunchDate { year, month, day ->
                datePickerFrom.updateDate(year, month, day)
            }
            launchesViewModel.getLastLaunchDate { year, month, day ->
                datePickerTo.updateDate(year, month, day)
            }

            confirmButton.setOnClickListener { applyFilters() }
            cancelButton.setOnClickListener { dialog?.dismiss() }
        }
    }

    private fun applyFilters() {
        val startDate = launchesViewModel.convertPickerToDate(binding.datePickerFrom)
        val endDate = launchesViewModel.convertPickerToDate(binding.datePickerTo)
        (targetFragment as LaunchesFragment).onFiltersApplied(startDate, endDate)
        dialog?.dismiss()
    }

    override fun onResume() {
        super.onResume()
        resizeDialogWindowToMatchScreen()
    }

    companion object {
        const val TAG = "DialogFragment"
    }
}


interface ApplyFilterDialogDelegate {
    // Called when dialog is about to close.
    fun onFiltersApplied(startDate: Date, endDate: Date)
}

