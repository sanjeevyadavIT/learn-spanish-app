package com.betatech.learnspanish.helper

import android.content.Context
import com.betatech.learnspanish.data.AppRepository
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.AppDbHelper
import com.betatech.learnspanish.data.local.db.DbHelper
import com.betatech.learnspanish.data.local.prefs.AppPreferencesHelper
import com.betatech.learnspanish.data.local.prefs.PreferencesHelper

/**
 * A Service Locator for the [AppRepository]. This is the mock version
 */
object ServiceLocator {

    @Volatile private var repository: Repository? = null

    fun provideRepository(context: Context): Repository {
        synchronized(this) {
            return repository ?: repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): Repository =
        AppRepository(createDbHelper(context), createPreferenceHelper(context))

    private fun createPreferenceHelper(context: Context): PreferencesHelper =
        AppPreferencesHelper.getInstance(context)

    private fun createDbHelper(context: Context): DbHelper =
        AppDbHelper.getInstance(createAppDatabase(context))

    private fun createAppDatabase(context: Context): AppDatabase =
        AppDatabase.getInstance(context)!!
}