package com.betatech.learnspanish.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import com.betatech.learnspanish.ui.settings.SettingsFragment

/**
 * After phone restart all alarms get cancel.
 *
 * Broadcast receiver responsible to reschedule
 * notification, if notification is turned on by user
 */
class RescheduleNotificationAfterBoot : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            val notificationOn = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(SettingsFragment.NOTIFICATION_ON_PREFERENCE_KEY, false)

            if (notificationOn) {
                NotificationUtil.scheduleAlarmToTriggerNotification(context)
            }
        }
    }
}