package com.betatech.learnspanish.data.local.prefs

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
}