package com.betatech.learnspanish

import android.app.Application
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.helper.ServiceLocator

/**
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample.
 */
class App : Application() {

    // Depends on the flavor,
    val repository: Repository
        get() = ServiceLocator.provideRepository(this)
}