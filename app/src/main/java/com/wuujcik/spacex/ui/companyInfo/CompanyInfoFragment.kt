package com.wuujcik.spacex.ui.companyInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wuujcik.spacex.R
import com.wuujcik.spacex.databinding.FragmentCompanyInfoBinding
import com.wuujcik.spacex.utils.formatCurrency
import com.wuujcik.spacex.utils.formatNumber

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
                employeesData.text = formatNumber(info?.employees)
                vehiclesData.text = formatNumber(info?.vehicles)
                launchSitesData.text = formatNumber(info?.launch_sites)
                testSitesData.text = formatNumber(info?.test_sites)
                valuationData.text = formatCurrency(info?.valuation)
                ceoData.text = info?.ceo
                ctoData.text = info?.cto
                cosData.text = info?.coo
                ctoPropulsionData.text = info?.cto_propulsion
            }
        })
    }
}