package com.wuujcik.spacex.ui.companyInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wuujcik.spacex.R
import com.wuujcik.spacex.databinding.FragmentCompanyInfoBinding

class CompanyInfoFragment : Fragment() {

    private val companyInfoViewModel by viewModels<CompanyInfoViewModel>()

    private lateinit var binding: FragmentCompanyInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCompanyInfoBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        companyInfoViewModel.requestCompanyInfo()

        with(binding) {

            companyInfoViewModel.companyInfo.observe(viewLifecycleOwner, { info ->
                binding.companyName.text = info?.name
                binding.founder.text =
                    context?.resources?.getString(R.string.founded, info?.founder, info?.founded)
                binding.summary.text = info?.summary
                binding.employeesData.text = info?.employees.toString() // TODO: use some nicer formatting of numbers
                binding.vehiclesData.text = info?.vehicles.toString()
                binding.launchSitesData.text = info?.launch_sites.toString()
                binding.testSitesData.text = info?.test_sites.toString()
                binding.valuationData.text = info?.valuation.toString() // TODO: add currency formatting
            })
        }

    }

    companion object {
        const val TAG = "CompanyInfoFragment"
    }
}