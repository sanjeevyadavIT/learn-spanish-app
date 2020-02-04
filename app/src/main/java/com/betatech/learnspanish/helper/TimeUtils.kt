package com.betatech.learnspanish.helper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtils {

    private val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)

    /**
     * Notification time is saved in preference as string in
     * the format dd:dd, where d is a single digit
     * for example, 9 pm is stored as 21:0
     *
     * this function parse this format and print it
     * properly like 8:00 pm
     *
     * @param time saved in shared preference
     */
    fun formatTimeSavedInPreference(time: String?): String {
        if (time == null) return ""

        try {
            val split = time.split(":")
            var hour: Int = split[0].toInt()
            val minute: Int = split[1].toInt()
            val timeSet: String

            when {
                hour > 12 -> {
                    hour -= 12
                    timeSet = "PM"
                }
                hour == 0 -> {
                    hour += 12
                    timeSet = "AM"
                }
                hour == 12 -> {
                    timeSet = "PM"
                }
                else -> {
                    timeSet = "AM"
                }
            }

            return "$hour:${if (minute < 10) "0" else ""}$minute $timeSet"
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            return ""
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            return ""
        }
    }

    fun convertDateToString(date: Date): String = dateFormat.format(date)

    fun convertStringToDate(string: String?): Date? {
        if (string == null) return null

        return try {
            dateFormat.parse(string)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * @return today's date
     */
    fun getCurrentDate(): Date = Calendar.getInstance().time

    /**
     * remove time from date and then subtract date
     * to get difference in number of days
     */
    fun subtractDates(currentDate: Date, lastPracticeDate: Date): Int {
        return try {
            val currentDateWithoutTime =
                dateFormat.parse(dateFormat.format(currentDate)) ?: return 0
            val lastPracticeDateWithoutTime =
                dateFormat.parse(dateFormat.format(lastPracticeDate)) ?: return 0

            val diff = currentDateWithoutTime.time - lastPracticeDateWithoutTime.time

            TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()

        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }
}