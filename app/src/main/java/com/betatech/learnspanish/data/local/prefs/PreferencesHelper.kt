package com.betatech.learnspanish.data.local.prefs

import java.util.*

/**
 * All Shared Preferences related functions
 */
interface PreferencesHelper {

    /**
     * Add experience points(XP) to existing score
     *
     * @param value amount of xp to be increased
     */
    fun addXp(value: Int)

    /**
     * Get user experience points total
     *
     * @return total xp of the user
     */
    fun getXp(): Int

    /**
     * Update the date which points to the
     * user last practice date, will be used
     * in calculating streak
     */
    fun updatePracticeDate(date: Date)

    /**
     * Retrieve the last date on which
     * user practiced.
     */
    fun getPracticeDate(): Date?

    /**
     * Increment the current streak by one
     */
    fun incrementStreak()

    /**
     * User lost streak, reset it to zero
     */
    fun resetStreak()

    /**
     * Get current streak
     */
    fun getStreak(): Int
}