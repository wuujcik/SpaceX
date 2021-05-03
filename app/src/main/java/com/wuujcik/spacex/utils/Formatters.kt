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

/**
 * Formats number to locale aware String
 */
fun formatNumber(number: Number?): String {
    val locale = Locale.getDefault()
    val sb = StringBuilder()
    val formatter = Formatter(sb, locale)
    return formatter.format(locale, "%,d", number).toString()
}

/**
 * Formats number to locale aware String in US dollars
 */
fun formatCurrency(amount: Number?): String {
    val locale = Locale.getDefault()
    val sb = StringBuilder()
    val formatter = Formatter(sb, locale)
    return formatter.format(locale, "$ %,d", amount).toString()
}
