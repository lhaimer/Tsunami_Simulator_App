package com.climateteam9.tsunamisimulator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.climateteam9.tsunamisimulator.databinding.ActivityDrawerBinding
import com.climateteam9.tsunamisimulator.utils.data.datastructure
import com.climateteam9.tsunamisimulator.utils.services.ApiInterface
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.datetime.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.fragment_home)


        binding.appBarDrawer.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        //val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )


        val txt=findViewById<TextView>(R.id.title3TV)
        txt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // start your next activity
            startActivity(intent)}



        getdata()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    fun getdata() {

        val eqPlace=findViewById<TextView>(R.id.quakeLocationTV)
        val eqTime=findViewById<TextView>(R.id.quakeDistanceTV)
        val eqMagnitude=findViewById<TextView>(R.id.quakeTimeTV)
        val eqSafetylevel=findViewById<TextView>(R.id.SafetyLevelTV)
        var safetylevelMSG:String




        val service = ApiInterface.create()

        val call: Call<datastructure> = service.fetchFact("2022-11-19","2022-11-30",48.866667,2.333333,10000,5,"time")
        call.enqueue(object : Callback<datastructure> {

            override fun onResponse(call: Call<datastructure>, response: Response<datastructure>) {
                val response = response.body()
                val EQaddress = response?.features?.get(0)?.properties?.place
                if (EQaddress != null) {
                    eqPlace.setText(response.features[0]?.properties?.place.toString())
                     } else{

                    if (EQaddress != null) {
                        eqPlace.setText(response?.features?.get(0)?.properties?.place.toString())
                    }

                     }
                eqPlace.setText(response?.features?.get(0)?.properties?.place.toString())
                eqMagnitude.setText(response?.features?.get(0)?.properties?.mag!!.toString())
                val timeSinceEpoch=response?.features?.get(0)?.properties?.time


                if (timeSinceEpoch != null) {
                    val EQTime = timeConverter(timeSinceEpoch.toLong()).toString()
                    eqTime.setText(EQTime)
                }
                if(response.features.get(0)!!.properties?.tsunami==0){safetylevelMSG="No Tsunami Alert"}else{safetylevelMSG="Tsunami Alert,Please check"}
                eqSafetylevel.setText(safetylevelMSG)


                Log.d("fitshdata, new try: ",response.toString())
                response?.features?.get(0)?.properties?.place?.let { Log.d("API DATA",
                    "Earthquake address: $it"
                ) }
                response?.features?.get(0)?.properties?.mag?.let { Log.d("API DATA","Earthquake magnitude: "+
                    it.toString()) }

                response?.features?.get(0)?.properties?.time?.let { Log.d("API DATA","Earthquake time: "+
                    it.toString()
                ) }

                response?.features?.get(0)?.properties?.tsunami?.let { Log.d("API DATA","Tsunami alert level: "+
                    it.toString()
                ) }
                response?.features?.get(0)?.geometry?.coordinates.let { Log.d("API DATA","Earthquake location: "+
                    it.toString()
                ) }
            }

            override fun onFailure(call: Call<datastructure>, t: Throwable) {
                t.message?.let { Log.d("fitshdata, new try:", it) }
            }
        })




        }

    fun timeConverter(timeSinceEpoch: Long): LocalTime {

        val timeZone = TimeZone.of("UTC+1")

        //https://github.com/Kotlin/kotlinx-datetime
        val EQinstant = Instant.fromEpochMilliseconds(timeSinceEpoch.toLong())
        val EQtimeInSystemZone: LocalDateTime = EQinstant.toLocalDateTime(TimeZone.currentSystemDefault())//UTC
        val EQtimeInSystemZone2: LocalTime = EQinstant.toLocalDateTime(TimeZone.of("UTC+1")).time


        Log.e("BELHAIMER", "la date et l'heure de l'evenement est : $EQtimeInSystemZone")
        Log.e("BELHAIMER", "l'heure de l'evenement est : $EQtimeInSystemZone2")


        return EQtimeInSystemZone2


    }

    }


