package com.climateteam9.tsunamisimulator

//import com.climateteam9.tsunamisimulator.utils.services.GetUserLocation
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.climateteam9.tsunamisimulator.Permission.PERMISSION_ID
import com.climateteam9.tsunamisimulator.databinding.ActivityDrawerBinding
import com.climateteam9.tsunamisimulator.utils.data.datastructure
import com.climateteam9.tsunamisimulator.utils.services.ApiInterface
import com.climateteam9.tsunamisimulator.utils.services.GetUserLocation
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.datetime.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerBinding
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.fragment_home)
        swipeRefreshLayout = findViewById(R.id.container)

        binding.appBarDrawer.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val closebtn:ImageView = findViewById(R.id.closeBTN)
        val txt=findViewById<TextView>(R.id.title3TV)
        val txt1=findViewById<TextView>(R.id.locationTV)
        val txt2=findViewById<TextView>(R.id.ContryTV)
        val txt3=findViewById<TextView>(R.id.nearestCostTV)
        //val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        val iv_click_me = findViewById<ImageView>(R.id.closeBTN)
        // set on-click listener
        iv_click_me.setOnClickListener {
            // your code to perform when the user clicks on the ImageView
            Toast.makeText(this, "You clicked on ImageView.", Toast.LENGTH_SHORT).show()
        }



        txt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // start your next activity
            startActivity(intent)}
        // to refresh layout
        swipeRefreshLayout.setOnRefreshListener{
            getdata()
            GetUserLocation(this).getLastLocation(txt1,txt2,txt3)
            // on below line we are setting is refreshing to false.
            swipeRefreshLayout.isRefreshing = false
        }


        getdata()
        GetUserLocation(this).getLastLocation(txt1,txt2,txt3)



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

        val call: Call<datastructure> = service.fetchFact("2022-11-20","2022-11-30",48.866667,2.333333,10000,5,"time")
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:","You have the Permission")
            }
        }
    }
    fun quit() {
        /*android:clickable="true"
        android:onClick="quit"*/
        val pid = Process.myPid()
        Process.killProcess(pid)
        exitProcess(0)
    }

    }


