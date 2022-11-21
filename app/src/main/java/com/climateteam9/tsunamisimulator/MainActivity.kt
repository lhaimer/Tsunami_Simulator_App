package com.climateteam9.tsunamisimulator

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.climateteam9.tsunamisimulator.utils.TsunamiScenarioStatus
import com.google.android.gms.location.*
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get NavHost and NavController
        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController = navHostFrag.navController

        // Get AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Link ActionBar with NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
        tsunamiSimulator()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun tsunamiSimulator(): String {

        var delayTime: Int
        var userSpeed: Int
        var userLocation: Int = 0
        var type: String
        var initialDistance: Int
        var power: Float


        // Read Preference values in an activity
        // Step 1: Get reference to the SharedPreferences (XML File)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        // Step 2: Get the 'value' using the 'key'
        delayTime = (sharedPreferences.getString(
            getString(R.string.key_values_warning_delay),
            "15"
        ))!!.toInt()

        Log.e("SettingsFragment", "Auto Reply Time: $delayTime")
        userSpeed =
            (sharedPreferences.getString(getString(R.string.key_mobility_type), "30000"))!!.toInt()
        Log.e("the tsunami parameters", "The mobility type is : $userSpeed")
        type = sharedPreferences.getString(getString(R.string.key_tsunami_reference), "Japan")!!
        Log.e("the tsunami parameters", "The tsunami reference : $type")
        power = (sharedPreferences.getString(
            getString(R.string.key_tsunami_wave_power),
            "1"
        ))!!.toFloat()
        Log.e("the tsunami parameters", "The tsunami wave power: $power")
        initialDistance =
            (sharedPreferences.getString(getString(R.string.key_wave_distance), "1000"))!!.toInt()
        Log.e("the tsunami parameters", "The tsunami wave distance is : $initialDistance")

        val message = TsunamiScenarioStatus(
            delayTime,
            userSpeed,
            userLocation,
            type,
            initialDistance,
            power
        )
            .UserSafetyStatus()
        return message


    }

}
