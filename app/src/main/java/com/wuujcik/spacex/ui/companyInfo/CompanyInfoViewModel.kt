package com.wuujcik.spacex.ui.companyInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wuujcik.spacex.persistence.companyInfo.CompanyInfo
import com.wuujcik.spacex.providers.CompanyInfoProvider

class CompanyInfoViewModel(app: Application) : AndroidViewModel(app) {

    private val provider: CompanyInfoProvider by lazy {
        CompanyInfoProvider(app)
    }

    val companyInfo: LiveData<CompanyInfo?> = provider.companyInfo

    fun requestCompanyInfo() {
        provider.requestCompanyInfo(viewModelScope)
    }
}
