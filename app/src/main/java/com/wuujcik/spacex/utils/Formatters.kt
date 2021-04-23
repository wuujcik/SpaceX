package com.wuujcik.spacex.utils

import android.content.Context
import android.text.format.DateUtils
import java.util.*

/**
 * formats the date to display day, numeric month, year and time in a localized format
 */
fun formatDateTime(context: Context, date: Date): String {
    return DateUtils.formatDateTime(
        context,
        date.time,
         DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NUMERIC_DATE
    )
}