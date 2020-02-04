package com.betatech.learnspanish.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.betatech.learnspanish.helper.TimeUtils
import java.util.*

class AppPreferencesHelper private constructor(
    context: Context
) : PreferencesHelper {

    private var preference: SharedPreferences

    init {
        preference = context.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE)
    }


    companion object {

        const val XP_PREF_KEY = "PREF_KEY_XP"
        const val LAST_PRACTICE_DATE_PREF_KEY = "PREF_KEY_LAST_PRACTICE_DATE"
        const val STREAK_COUNT_PREF_KEY = "PREF_KEY_STREAK_COUNT"

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
            edit().putInt(XP_PREF_KEY, previousValue + value).apply()
        }
    }

    override fun getXp(): Int = preference.getInt(XP_PREF_KEY, 0)

    override fun updatePracticeDate(date: Date) {
        preference.run {
            edit().putString(LAST_PRACTICE_DATE_PREF_KEY, TimeUtils.convertDateToString(date))
                .apply()
        }
    }

    override fun getPracticeDate(): Date? =
        TimeUtils.convertStringToDate(
            preference.getString(LAST_PRACTICE_DATE_PREF_KEY, "")
        )

    override fun incrementStreak() {
        val previousStreak = getStreak()
        preference.run {
            edit().putInt(STREAK_COUNT_PREF_KEY, previousStreak + 1).apply()
        }
    }

    override fun resetStreak() {
        preference.run {
            edit().putInt(STREAK_COUNT_PREF_KEY, 0).apply()
        }
    }

    override fun getStreak(): Int = preference.getInt(STREAK_COUNT_PREF_KEY, 0)

}