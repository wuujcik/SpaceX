package com.wuujcik.spacex.ui.launches

import android.app.Application
import android.widget.DatePicker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.wuujcik.spacex.persistence.launch.Launch
import com.wuujcik.spacex.persistence.launch.LaunchesDataSource
import java.util.*


class LaunchesViewModel(app: Application) : AndroidViewModel(app) {

    private fun getLaunches(): LaunchesDataSource.LaunchesDataSourceFactory {
        return LaunchesDataSource(viewModelScope).LaunchesDataSourceFactory()
    }

    val launches: LiveData<PagedList<Launch>> = getLaunches().toLiveData(10)


    fun applyFilter(
        startDate: Date,
        endDate: Date
    ) {
//TODO
    }


    fun convertPickerToDate(picker: DatePicker): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, picker.year)
        calendar.set(Calendar.MONTH, picker.month)
        calendar.set(Calendar.DAY_OF_MONTH, picker.dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        return calendar.time
    }
}
