package com.klt.util

import java.text.DateFormat
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.util.*

object TimeManager {

    /** Return the name of the current day as a string */
    fun getCurrentNameOfDayAsString(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeekString = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> "Invalid day of week"
        }
        return dayOfWeekString
    }

    /** Get current date nicely formatted */
    fun getCurrentDateAsString(): String {
        val dateFormat = getDateInstance()
        val date = Date()
        return dateFormat.format(date)
    }



}