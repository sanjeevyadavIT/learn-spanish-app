package com.betatech.learnspanish.data.local.prefs

import android.content.Context
import android.content.SharedPreferences

class AppPreferencesHelper private constructor(
    context: Context
) : PreferencesHelper {

    private var preference: SharedPreferences

    init {
        preference = context.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE)
    }


    companion object {

        const val PREF_KEY_XP = "PREF_KEY_XP"

        private const val PREFERENCES_FILENAME = "LearnSpanish"

        // For Singleton instantiation
        @Volatile
        private var INSTANCE: AppPreferencesHelper? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppPreferencesHelper(context).also { INSTANCE = it }
            }
    }

    override fun addXp(value: Int) {
        val previousValue = getXp()
        preference.run {
            edit().putInt(PREF_KEY_XP, previousValue + value).apply()
        }
    }

    override fun getXp(): Int = preference.getInt(PREF_KEY_XP, 0)

}