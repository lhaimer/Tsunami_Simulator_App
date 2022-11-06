package com.climateteam9.tsunamisimulator

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.*



class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val dataStore = DataStore()
        // Enable PreferenceDataStore for entire hierarchy and disables the SharedPreferences
//        preferenceManager.preferenceDataStore = dataStore

        val accSettingsPref = findPreference<Preference>(getString(R.string.key_account_settings))

        accSettingsPref?.setOnPreferenceClickListener {

            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)
            true
        }

        // Read Preference values in a Fragment
        // Step 1: Get reference to the SharedPreferences (XML File)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        // Step 2: Get the 'value' using the 'key'
        val autoReplyTime = sharedPreferences.getString(getString(R.string.key_auto_reply_time), "")
        Log.i("SettingsFragment", "Auto Reply Time: $autoReplyTime")

        val autoDownload =
            sharedPreferences.getBoolean(getString(R.string.key_auto_download), false)
        Log.i("SettingsFragment", "Auto Download: $autoDownload")

        val statusPref = findPreference<EditTextPreference>(getString(R.string.key_status))
        statusPref?.setOnPreferenceChangeListener { preference, newValue ->

            val newStatus = newValue as String
            if (newStatus.contains("bad")) {
                Toast.makeText(
                    context, "Inappropriate Status. Please maintain community guidelines.",
                    Toast.LENGTH_SHORT
                ).show()

                false   // false: reject the new value.
            } else {
                true     // true: accept the new value.
            }
        }

        val notificationPref =
            findPreference<SwitchPreferenceCompat>(getString(R.string.key_new_msg_notif))
        notificationPref?.summaryProvider =
            Preference.SummaryProvider<SwitchPreferenceCompat> { switchPref ->

                if (switchPref?.isChecked!!)
                    "Status: ON"
                else
                    "Status: OFF"
            }

        notificationPref?.preferenceDataStore = dataStore

        val isNotifEnabled = dataStore.getBoolean("key_new_msg_notif", false)
    }

    class DataStore : PreferenceDataStore() {

        // Override methods only as per your need.
        // DO NOT override methods which you don't need to use.
        // After overriding, remove the super call. (could throw UnsupportedOperationException)

        override fun getBoolean(key: String?, defValue: Boolean): Boolean {

            if (key == "key_new_msg_notif") {
                // Retrieve value from cloud or local db
                Log.i("DataStore", "getBoolean executed for $key")
            }

            return defValue
        }

        override fun putBoolean(key: String?, value: Boolean) {

            if (key == "key_new_msg_notif") {
                // Save value to cloud or local db
                Log.i("DataStore", "putBoolean executed for $key with new value: $value")
            }
        }
    }
}