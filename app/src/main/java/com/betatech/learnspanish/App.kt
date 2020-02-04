package com.betatech.learnspanish

import android.app.Application
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.helper.ServiceLocator
import com.betatech.learnspanish.helper.TimeUtils
import com.betatech.learnspanish.notification.NotificationUtil
import java.util.*

/**
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample.
 */
class App : Application() {

    // Depends on the flavor,
    val repository: Repository
        get() = ServiceLocator.provideRepository(this)

    override fun onCreate() {
        super.onCreate()
        // It's safe to call this repeatedly because creating an existing notification channel performs no operation.
        NotificationUtil.createNotificationChannel(applicationContext)
        evaluateStreak()
    }

    private fun evaluateStreak() {
        val currentDate: Date = TimeUtils.getCurrentDate()
        val lastPracticeDate: Date = repository.getPracticeDate() ?: return

        val differenceOfDays: Int = TimeUtils.subtractDates(currentDate, lastPracticeDate)

        if (differenceOfDays > 1) {
            // user is not continuous, reset the streak
            repository.resetStreak()
        }
    }
}