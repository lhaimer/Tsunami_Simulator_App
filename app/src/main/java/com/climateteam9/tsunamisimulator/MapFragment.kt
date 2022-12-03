package com.climateteam9.tsunamisimulator

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.climateteam9.tsunamisimulator.utils.TsunamiRiskCalculatorConstructorKotlin
import com.climateteam9.tsunamisimulator.utils.TsunamiScenarioStatus
import kotlinx.android.synthetic.main.fragment_map.view.*


class MapFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        //return inflater.inflate(R.layout.fragment_map, container, false)
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_map, container, false)

                  //add your view before id else getting null pointer exception

        view.btnResutls.setOnClickListener {
            //= tsunamiSimulator()
            val msg = TsunamiRiskCalculatorConstructorKotlin(1400.0,8.9F,OceanProx = true)
            view.messageTV.text = msg.Notify()
        }

            return view
        }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.chat_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> {
                val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
                val navController = navHostFragment.navController
                val action = MapFragmentDirections.actionChatListToSettings()
                navController.navigate(action)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
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
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext(). applicationContext)
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