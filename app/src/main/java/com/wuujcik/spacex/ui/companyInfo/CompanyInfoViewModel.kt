package com.wuujcik.spacex.ui.companyInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.spacex.persistence.CompanyInfo
import com.wuujcik.spacex.providers.CompanyInfoProvider

class CompanyInfoViewModel : ViewModel() {

    private val provider: CompanyInfoProvider by lazy {
        CompanyInfoProvider()
    }

    val companyInfo: LiveData<CompanyInfo?> = provider.companyInfo

    fun requestCompanyInfo() {
        provider.getCompanyInfo(viewModelScope)
    }
}
