package com.climateteam9.tsunamisimulator

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.climateteam9.tsunamisimulator.Permission.PERMISSION_ID
import com.climateteam9.tsunamisimulator.utils.services.GetUserAndEarthquakeData
import com.climateteam9.tsunamisimulator.databinding.ActivityDrawerBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


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
        val txt1=findViewById<TextView>(R.id.locationTV)
        val txt2=findViewById<TextView>(R.id.contryTV)
        val txt3=findViewById<TextView>(R.id.nearestCostTV)
        val t1=findViewById<TextView>(R.id.quakeLocationTV)
        val t2=findViewById<TextView>(R.id.quakeDistanceTV)
        val t3=findViewById<TextView>(R.id.quakeTimeTV)
        val t4=findViewById<TextView>(R.id.SafetyLevelTV)
        val closeIV=findViewById<ImageView>(R.id.closeIV)
        lateinit var swipeContainer: SwipeRefreshLayout
        swipeContainer = findViewById(R.id.swipeContainer)

        txt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // start your next activity
            startActivity(intent)}
        closeIV.setOnClickListener {
            finish()
            System.out.close()
            }

        GetUserAndEarthquakeData(this).getLastLocation(txt1,txt2,txt3,t1,t2,t3,t4)

        // Setup refresh listener which triggers new data loading

       /* swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            GetUserAndEarthquakeData(this).getLastLocation(txt1,txt2,txt3,t1,t2,t3,t4)
            swipeContainer.isRefreshing = false
        }*/


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

    }