package com.betatech.learnspanish.helper

object TimeUtils {

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
}