package com.wuujcik.spacex.ui.launches

import android.app.Application
import android.util.Log
import android.widget.DatePicker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.wuujcik.spacex.persistence.launch.Launch
import com.wuujcik.spacex.persistence.launch.LaunchesDataSourceFactory
import com.wuujcik.spacex.providers.LaunchProvider
import com.wuujcik.spacex.utils.toIsoDate
import org.json.JSONObject
import java.util.*


class LaunchesViewModel(app: Application) : AndroidViewModel(app) {

    private val queryArgs = JSONObject()
    private fun getLaunches(queryArgs: JSONObject): LaunchesDataSourceFactory {
        return LaunchesDataSourceFactory(queryArgs, viewModelScope)
    }

    private val provider: LaunchProvider by lazy {
        LaunchProvider(app)
    }

    var launches: LiveData<PagedList<Launch>> = getLaunches(queryArgs).toLiveData(10)

    fun getFirstLaunchDate(callback: (year: Int, month: Int, day: Int) -> Unit) {
        provider.getLaunches(viewModelScope, LaunchProvider.SORT_ASC) { launch ->
            val timestamp = launch?.date_unix
            val calendar = convertTimesstampToDate(timestamp ?: Date().time)
            callback(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
    }

    fun getLastLaunchDate(callback: (year: Int, month: Int, day: Int) -> Unit) {
        provider.getLaunches(viewModelScope, LaunchProvider.SORT_DESC) { launch ->
            val timestamp = launch?.date_unix
            val calendar = convertTimesstampToDate(timestamp ?: Date().time)
            callback(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
    }

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

    private fun convertTimesstampToDate(timestampInSec: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = Date(timestampInSec * 1000) // to milliseconds
        return calendar
    }
}
