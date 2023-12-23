package com.harison.core.app.utils.extensions

import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_12HOUR
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_12HOUR_EXTRA
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_24HOUR
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_24HOUR_EXTRA
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_FULL
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_FULL_EXTRA
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_FULL_NO_ZONE
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_FULL_UTC
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_SOURCE
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DATE_TIME_Z
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DAY_AND_SHORT_MONTH
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_DAY_OF_WEEK
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_FULL
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_FULL_DAY_OF_WEEK_DATE
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_FULL_MCA
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_FULL_MONTH_YEAR
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_MCA_YEAR_MONTH
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_MONTH_YEAR
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_SIMPLE
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_TIME_12HOUR
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_TIME_12HOUR_AM
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_TIME_12HOUR_EXTRA
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_TIME_24HOUR
import com.harison.core.app.utils.extensions.DateFormat.DATE_FORMAT_TIME_MINUTE
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import org.joda.time.format.DateTimeFormat
import timber.log.Timber
import kotlin.time.Duration.Companion.milliseconds

private val knownPatterns = listOf(
    DATE_FORMAT_DATE_TIME_FULL,
    DATE_FORMAT_DATE_TIME_FULL_UTC,
    DATE_FORMAT_DATE_TIME_FULL_EXTRA,
    DATE_FORMAT_DATE_TIME_Z,
    DATE_FORMAT_DATE_TIME_SOURCE,
    DATE_FORMAT_DATE_TIME,
    DATE_FORMAT_DATE_TIME_FULL_NO_ZONE,
    DATE_FORMAT_DATE_TIME_12HOUR,
    DATE_FORMAT_DATE_TIME_12HOUR_EXTRA,
    DATE_FORMAT_DATE_TIME_24HOUR,
    DATE_FORMAT_DATE_TIME_24HOUR_EXTRA,
    DATE_FORMAT_DATE,
    DATE_FORMAT_DAY_AND_SHORT_MONTH,
    DATE_FORMAT_SIMPLE,
    DATE_FORMAT_FULL,
    DATE_FORMAT_FULL_MONTH_YEAR,
    DATE_FORMAT_FULL_DAY_OF_WEEK_DATE,
    DATE_FORMAT_FULL_MCA,
    DATE_FORMAT_MCA_YEAR_MONTH,
    DATE_FORMAT_TIME_12HOUR,
    DATE_FORMAT_TIME_12HOUR_AM,
    DATE_FORMAT_TIME_12HOUR_EXTRA,
    DATE_FORMAT_MONTH_YEAR,
    DATE_FORMAT_TIME_24HOUR,
    DATE_FORMAT_TIME_MINUTE,
    DATE_FORMAT_DAY_OF_WEEK
)

/** Convert second to hour:minutes:seconds
 * */
fun formatDurationTime(durationSeconds: Long) =
    durationSeconds.milliseconds.toComponents { hours, minutes, seconds, _ ->
        Timber.d("--- Time: $minutes:$seconds")
        String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )
    }

fun Long.toDateTime(
    dateFormat: String,
    zone: ZoneId
): String {
    val date = Date(this).convertFormatDateTime(dateFormat)
    return date?.convertFormatDateTime(dateFormat, zone).toString()
}

fun getCurrentDay(): Int {
    return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
}

fun getCurrentMonth(): Int {
    return Calendar.getInstance().get(Calendar.MONTH)
}

fun getCurrentYear(): Int {
    return Calendar.getInstance().get(Calendar.YEAR)
}

fun getTime(timeFormat: String, time: Date): String {
    val dateFormatter = SimpleDateFormat(timeFormat, Locale.getDefault())
    return dateFormatter.format(time)
}

fun getDate(timeFormat: String, time: String): Date {
    val dateFormatter = SimpleDateFormat(timeFormat, Locale.ENGLISH)
    return dateFormatter.parse(time) ?: Date()
}

fun Date.convertFormatDateTime(
    dateFormat: String,
    locale: Locale? = Locale.ENGLISH
): String? {
    try {
        val simpleDateFormat = SimpleDateFormat(dateFormat, locale)
        return simpleDateFormat.format(this)
    } catch (e: Exception) {
    }
    return null
}

fun String.convertFormatDateTime(
    dateFormat: String,
    zone: ZoneId,
    locale: Locale? = Locale.ENGLISH
): String? {
    try {
        val dateTime = this.getDateTime(zone, locale)
        return dateTime?.convertFormatDateTime(dateFormat, locale)
    } catch (e: Exception) {
    }
    return null
}

fun String.getDateTime(
    zoneId: ZoneId,
    locale: Locale? = Locale.ENGLISH
): Date? {
    for (dateFormat in knownPatterns) {
        try {
            val parser = DateTimeFormat.forPattern(dateFormat)
            val localDate = parser.parseLocalDate(this)
            val localTime = parser.parseLocalTime(this)
            val localDateTime = localDate.toLocalDateTime(localTime)
            return localDateTime.toDate()
        } catch (e: Exception) {
        }
    }
    return null
}

