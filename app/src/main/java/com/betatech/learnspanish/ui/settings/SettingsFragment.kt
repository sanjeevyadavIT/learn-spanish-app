package com.betatech.learnspanish.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.betatech.learnspanish.R
import com.betatech.learnspanish.helper.TimeUtils
import com.betatech.learnspanish.notification.NotificationUtil

/**
 * Note: As preference for time is set in different fragment, setting
 *     onPreferenceChangeListener on that preference doesn't work,
 *     as a work around [SharedPreferences.OnSharedPreferenceChangeListener]
 *     need to be used.
 */
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        const val NOTIFICATION_PREFERENCE_KEY = "preference_notification"
        const val NOTIFICATION_TIME_PREFERENCE_KEY = "preference_notification_time"
        const val DFAULT_NOTIFICATION_TIME = "21:0"
    }

    private var notificationSwitchPreference: SwitchPreference? = null
    private var notificationTimePreference: Preference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings, rootKey)
        init()
        setupClickListeners()
        setupInitialSummary()
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onPreferenceClick(preference: Preference?): Boolean =
        when (preference?.key) {
            NOTIFICATION_TIME_PREFERENCE_KEY -> {
                findNavController().navigate(R.id.action_settingsFragment_to_timePickerFragment)
                true
            }
            else -> false
        }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, pref_key: String?) {
        when (pref_key) {
            NOTIFICATION_TIME_PREFERENCE_KEY -> {
                notificationTimePreference?.summary =
                    TimeUtils.formatTimeSavedInPreference(
                        prefs?.getString(
                            pref_key,
                            DFAULT_NOTIFICATION_TIME
                        )
                    )
                val isNotificationOn =
                    prefs?.getBoolean(NOTIFICATION_PREFERENCE_KEY, false) ?: false
                if (isNotificationOn) {
                    context?.let { NotificationUtil.scheduleAlarmToTriggerNotification(it) }
                }
            }
            NOTIFICATION_PREFERENCE_KEY -> {
                val value = prefs?.getBoolean(pref_key, false) ?: false
                context?.let {
                    NotificationUtil.toggleNotificationRestartAfterBoot(it, value)
                    if (value) {
                        NotificationUtil.scheduleAlarmToTriggerNotification(it)
                    } else {
                        NotificationUtil.cancelAlarmToTriggerNotification(it)
                    }
                }
            }
        }
    }

    private fun init() {
        notificationSwitchPreference = findPreference(NOTIFICATION_PREFERENCE_KEY)
        notificationTimePreference = findPreference(NOTIFICATION_TIME_PREFERENCE_KEY)
    }

    private fun setupClickListeners() {
        notificationTimePreference?.onPreferenceClickListener = this
    }

    /**
     * NOTE: Tried setting up summary provider
     * for [notificationTimePreference] but it clashed with
     * [onSharedPreferenceChanged] function, hence removed summary
     * provider with summary
     */
    private fun setupInitialSummary() {
        notificationTimePreference?.summary = TimeUtils.formatTimeSavedInPreference(
            preferenceManager.sharedPreferences.getString(
                NOTIFICATION_TIME_PREFERENCE_KEY, DFAULT_NOTIFICATION_TIME
            )
        )
    }
}
