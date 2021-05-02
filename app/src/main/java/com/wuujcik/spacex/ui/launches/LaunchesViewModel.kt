package com.wuujcik.spacex.ui.launches

import android.app.Application
import android.widget.DatePicker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.wuujcik.spacex.persistence.launch.Launch
import com.wuujcik.spacex.persistence.launch.LaunchesDataSourceFactory
import com.wuujcik.spacex.utils.toIsoDate
import org.json.JSONObject
import java.util.*


class LaunchesViewModel(app: Application) : AndroidViewModel(app) {

    private val queryArgs = JSONObject()
    private fun getLaunches(queryArgs: JSONObject): LaunchesDataSourceFactory {
        return LaunchesDataSourceFactory(queryArgs, viewModelScope)
    }

    var launches: LiveData<PagedList<Launch>> = getLaunches(queryArgs).toLiveData(10)

    fun applyFilter(
        startDate: Date,
        endDate: Date
    ) {
        val datesUtsFilter = JSONObject().put("\$gte", startDate.toIsoDate())
            .put("\$lte", endDate.toIsoDate())
        queryArgs.put("date_utc", datesUtsFilter)
        launches = getLaunches(queryArgs).toLiveData(10)
        launches.value?.dataSource?.invalidate()
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
