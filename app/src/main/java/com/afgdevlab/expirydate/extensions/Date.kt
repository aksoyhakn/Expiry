package com.afgdevlab.expirydate.extensions

import com.afgdevlab.expirydate.utils.Constants
import java.sql.Time
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

val CURRENT_TIME: Date = Date(Time(System.currentTimeMillis()).time)

val CURRENT_HOUR: Int =
    SimpleDateFormat("HH").format(Date(Time(System.currentTimeMillis()).time)).toInt()

fun Long.getTimeAfterDay(day: Int): Date {
    return Date(this + (86400000 * day))
}

val Long.hourOfDay: Int
    get() = SimpleDateFormat("HH").format(this).toInt()

val Long.minuteOfHour: Int
    get() = SimpleDateFormat("mm").format(this).toInt()

val Long.hour: Int
    get() = (this.toDouble() / 3600000).toInt()

val Int.convertToLongAsHour: Long
    get() = (this * 3600000).toLong()

val Int.convertToLongAsMinute: Long
    get() = (this * 60000).toLong()


val Long.hourAsString: String
    get() = (this / 3600000).toString()


val Long.minuteAsString: String
    get() = (this / 60000).toString()


val Long.monthOfYear: Int
    get() = SimpleDateFormat("MM").format(Date(this)).toInt()


val Long.dayOfMonth: Int
    get() = SimpleDateFormat("dd").format(Date(this)).toInt()

val Long.monthOfLongName: String
    get() = SimpleDateFormat("MMMM", Locale.getDefault()).format(Date(this)).toString()

val Long.monthOfName: String
    get() = SimpleDateFormat("MMM", Locale.getDefault()).format(Date(this)).toString()

val Long.dayOfWeek: String
    get() = SimpleDateFormat("EEE").format(Date(this))

val Long.year: String
    get() = SimpleDateFormat("yyyy").format(Date(this))

fun Long.getAsFormat(format: String, locale: Locale?): String {
    if (locale != null) {
        return SimpleDateFormat(format, locale).format(Date(this))
    }
    return SimpleDateFormat(format).format(Date(this))
}

fun Double.getFormater():String{
    val format = DecimalFormat()
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}

val Date.isPM: Boolean
    get() = this.time.hour >= 12


val Date.isAM: Boolean
    get() = this.time.hour < 12

val Date.isMorning: Boolean
    get() = this.time.hour in 6..11

val Date.isDay: Boolean
    get() = this.time.hour in 12..19

val Date.isEvening: Boolean
    get() = this.time.hour in 20..24

val Date.isNight: Boolean
    get() = this.time.hour < 6

val Date.isToday: Boolean
    get() {
        if (this.time.monthOfYear != CURRENT_TIME.time.monthOfYear) {
            return false
        }

        return this.time.dayOfMonth == CURRENT_TIME.time.dayOfMonth
    }

val Date.isYesterday: Boolean
    get() {
        if (this.time.monthOfYear != CURRENT_TIME.time.monthOfYear) {
            return false
        }

        return (CURRENT_TIME.time.dayOfMonth - this.time.dayOfMonth) == 1
    }

fun String.getDate(dateFormat: String): Date?  = SimpleDateFormat(dateFormat).parse(this)

val Date.withoutHourAndMinuteAsString: Long
    get() {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val strDate = formatter.format(time)
        return formatter.parse(strDate).time
    }

fun Date.getTimeStamp(): String {
    return SimpleDateFormat(Constants.App.TIMESTAMP_FORMAT, Locale.getDefault()).format(this)
}