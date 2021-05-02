package com.wuujcik.spacex.ui.launches

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
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


class LaunchesViewModel(val app: Application) : AndroidViewModel(app) {

    private val queryArgs = JSONObject()
    private fun getLaunches(queryArgs: JSONObject): LaunchesDataSourceFactory {
        return LaunchesDataSourceFactory(queryArgs, viewModelScope)
    }

    private val provider: LaunchProvider by lazy {
        LaunchProvider(app)
    }

    private val sharedPreferences: SharedPreferences
        get() {
            return app.getSharedPreferences(
                SHARED_PREF_KEY,
                Context.MODE_PRIVATE
            )
        }

    var launches: LiveData<PagedList<Launch>> = getLaunches(queryArgs).toLiveData(10)

    fun getFirstLaunchDate(callback: (year: Int, month: Int, day: Int) -> Unit) {
        val startYear = sharedPreferences.getInt(START_YEAR, 0)
        val startMonth = sharedPreferences.getInt(START_MONTH, 0)
        val startDay = sharedPreferences.getInt(START_DAY, 0)

        if (startYear == 0 || startMonth == 0 || startDay == 0) {
            provider.getLaunches(viewModelScope, LaunchProvider.SORT_ASC) { launch ->
                val timestamp = launch?.date_unix
                val calendar = convertTimestampToDate(timestamp ?: Date().time)
                saveStartDateToSharedPrefs(calendar.time)
                callback(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            }
        } else {
            callback(startYear, startMonth, startDay)
        }
    }

    fun getLastLaunchDate(callback: (year: Int, month: Int, day: Int) -> Unit) {
        val endYear = sharedPreferences.getInt(END_YEAR, 0)
        val endMonth = sharedPreferences.getInt(END_MONTH, 0)
        val endDay = sharedPreferences.getInt(END_DAY, 0)

        if (endYear == 0 || endMonth == 0 || endDay == 0) {
            provider.getLaunches(viewModelScope, LaunchProvider.SORT_DESC) { launch ->
                val timestamp = launch?.date_unix
                val calendar = convertTimestampToDate(timestamp ?: Date().time)
                saveStartDateToSharedPrefs(calendar.time)
                callback(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            }
        } else {
            callback(endYear, endMonth, endDay)
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

    private fun convertTimestampToDate(timestampInSec: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = Date(timestampInSec * 1000) // to milliseconds
        return calendar
    }

    fun saveStartDateToSharedPrefs(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        with(sharedPreferences.edit()) {
            putInt(START_YEAR, year)
            putInt(START_MONTH, month)
            putInt(START_DAY, day)
            apply()
        }
    }

    fun saveEndDateToSharedPrefs(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        with(sharedPreferences.edit()) {
            putInt(END_YEAR, year)
            putInt(END_MONTH, month)
            putInt(END_DAY, day)
            apply()
        }
    }


    companion object {
        const val SHARED_PREF_KEY = "com.wuujcik.spacex"
        const val START_YEAR = "START_YEAR"
        const val START_MONTH = "START_MONTH"
        const val START_DAY = "START_DAY"
        const val END_YEAR = "END_YEAR"
        const val END_MONTH = "END_MONTH"
        const val END_DAY = "END_DAY"
    }
}
