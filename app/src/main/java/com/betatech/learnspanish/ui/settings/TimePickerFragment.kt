package com.betatech.learnspanish.ui.settings

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import java.util.*


/**
 * TimePickerFragment to allow user to set time at which notification will be shown
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c: Calendar = Calendar.getInstance()
        setupInitialTime(c)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(
            activity, this, c[Calendar.HOUR_OF_DAY], c[Calendar.MINUTE], false
        )

    }

    private fun setupInitialTime(c: Calendar) {
        try {
            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(activity /* Activity context */)
            val (hour, minute) = sharedPreferences.getString(
                SettingsFragment.NOTIFICATION_TIME_PREFERENCE_KEY,
                SettingsFragment.DFAULT_NOTIFICATION_TIME
            )!!.split(":")
            c.set(Calendar.HOUR_OF_DAY, hour.toInt())
            c.set(Calendar.MINUTE, minute.toInt())
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(activity /* Activity context */)
        sharedPreferences.edit()
            .putString(SettingsFragment.NOTIFICATION_TIME_PREFERENCE_KEY, "$hourOfDay:$minute")
            .apply()
    }
}