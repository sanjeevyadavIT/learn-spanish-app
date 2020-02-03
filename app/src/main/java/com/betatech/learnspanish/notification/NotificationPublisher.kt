package com.betatech.learnspanish.notification

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

/**
 * Broadcast receiver for Alarm which is responsible
 * to show notification, to remind user to practice
 *
 */
class NotificationPublisher : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID_KEY = "notificationId"
        const val NOTIFICATION_KEY = "notification"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = NotificationManagerCompat.from(context)

        val notification: Notification? = intent.getParcelableExtra(NOTIFICATION_KEY)
        val notificationId = intent.getIntExtra(NOTIFICATION_ID_KEY, 0)

        if (notification != null) {
            notificationManager.notify(notificationId, notification)
        }

    }

}