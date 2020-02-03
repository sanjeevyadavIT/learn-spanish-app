package com.betatech.learnspanish

import android.app.Application
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.helper.ServiceLocator
import com.betatech.learnspanish.notification.NotificationUtil

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
    }
}