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



        companyInfoViewModel.companyInfo.observe(viewLifecycleOwner, { info ->
            with(binding) {
                companyName.text = info?.name
                founder.text =
                    context?.resources?.getString(R.string.founded, info?.founder, info?.founded)
                summary.text = info?.summary
                employeesData.text =
                    info?.employees.toString() // TODO: use some nicer formatting of numbers
                vehiclesData.text = info?.vehicles.toString()
                launchSitesData.text = info?.launch_sites.toString()
                testSitesData.text = info?.test_sites.toString()
                valuationData.text =
                    info?.valuation.toString() // TODO: add currency formatting
                ceoData.text = info?.ceo
                ctoData.text = info?.cto
                cosData.text = info?.coo
                ctoPropulsionData.text = info?.cto_propulsion
            }
        })
    }

    companion object {
        const val TAG = "CompanyInfoFragment"
    }
}