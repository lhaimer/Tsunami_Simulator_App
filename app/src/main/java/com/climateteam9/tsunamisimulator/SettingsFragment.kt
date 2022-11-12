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

/*        //get the tsunami parameters locally
        //val warnDelay = findPreference<Preference>(getString(R.string.key_values_warning_delay))
       // Log.e("the tsunami parameters", "warning delay time is : $warnDelay ")
        val mobilityType = findPreference<Preference>(getString(R.string.key_mobility_type))
        Log.e("the tsunami parameters", "warning delay time is : $mobilityType")
        val tsunamiReference = findPreference<Preference>(getString(R.string.key_tsunami_reference))
        Log.e("the tsunami parameters", "warning delay time is : $tsunamiReference")
        val tsunamiWavePower = findPreference<Preference>(getString(R.string.key_tsunami_wave_power))
        Log.e("the tsunami parameters", "warning delay time is : $tsunamiWavePower")*/

        // Read Preference values in a Fragment
        // Step 1: Get reference to the SharedPreferences (XML File)


        val dataStore = DataStore()

        // Enable PreferenceDataStore for entire hierarchy and disables the SharedPreferences
//        preferenceManager.preferenceDataStore = dataStore

        val accSettingsPref = findPreference<Preference>(getString(R.string.key_account_settings))
         //we can't access to the value of accSettingsPref out of the current fragment
        accSettingsPref?.setOnPreferenceClickListener {

            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)
            true
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