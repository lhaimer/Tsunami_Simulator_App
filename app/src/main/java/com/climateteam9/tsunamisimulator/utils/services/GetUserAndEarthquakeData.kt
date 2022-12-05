package com.climateteam9.tsunamisimulator.utils.services

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.climateteam9.tsunamisimulator.utils.data.datastructure
import com.google.android.gms.location.*
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class GetUserAndEarthquakeData(contextIn: Context) {

    //Declaring the needed Variables
    val context:Context = contextIn
    private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    lateinit var locationRequest: LocationRequest
    private val PermitionId = 1010

    //get user location

    fun getLastLocation(text1:TextView,text2:TextView,text3:TextView,
                        t1:TextView,t2:TextView,t3:TextView,t4:TextView){
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    val location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        val textLocation: TextView = text1
                        val textCity: TextView = text2
                        val textCost: TextView = text3

                       // Log.d("Debug:" ,"Your Location:"+ location.latitude)
                       // Log.d("Debug:" ,"Your Location:"+ location.longitude)
                       // Log.d("Debug:" ,"Your Location:"+ location.altitude)
                        textLocation.text = getCityName(location.latitude,location.longitude)[1]
                        textCity.text = getCityName(location.latitude,location.longitude)[0]
                        textCost.text = "${location.latitude},${location.longitude}"
                        getdata(t1,t2,t3,t4,location.latitude,location.longitude)
                       // Log.d("Debug:" ,"Your Location:"+ getCityName(location.latitude,location.longitude))


                    }
                }
            }else{
                Toast.makeText(context,"Please Turn on Your device Location", Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }

    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location? = locationResult.lastLocation
            if (lastLocation != null) {
                Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            }
            //val textView : TextView = findViewById(R.id.textView)
            //textView.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }

    private fun CheckPermission():Boolean{
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if(
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }

    fun RequestPermission(){
        //this function will allows us to tell the user to requesut the necessary permsition if they are not garented
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PermitionId
        )
    }



    fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false-AppCompatActivity.LOCATION_SERVICE
        val locationManager: LocationManager? = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }



    private fun getCityName(lat: Double,long: Double): ArrayList<String> {

        var cityName:String = ""
        var countryName = ""
        var cityContry =""
        val geoCoder = Geocoder(context, Locale.getDefault())
        val Adress = geoCoder.getFromLocation(lat,long,3)

        if (Adress != null) {
            cityName = Adress[0].locality


        }
        countryName = Adress?.get(0)?.countryName ?: "your location ..."
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        cityContry = cityName+"/"+countryName
        val list = arrayListOf<String>()
       list.addAll(listOf(cityName, countryName))

        return list
    }

    //get data from api
    fun getdata(t1:TextView,t2:TextView,t3:TextView,t4:TextView,lat:Double,long :Double) {

        val eqPlace=t1
        val eqTime=t2
        val eqMagnitude=t3
        val eqSafetylevel=t4
        val long :Double = long
        val lat:Double = lat
        var safetylevelMSG:String
        val systemTZ = TimeZone.currentSystemDefault()
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val tomorrow = now.plus(10, DateTimeUnit.DAY, systemTZ)



        val service = ApiInterface.create()

        val call: Call<datastructure> = service.fetchFact(today.toString(),tomorrow.toString(),lat,long,10000,5,"time")
        call.enqueue(object : Callback<datastructure> {

            override fun onResponse(call: Call<datastructure>, response: Response<datastructure>) {
                val response = response.body()
                val eQaddress = response?.features?.get(0)?.properties?.place
                if (eQaddress != null) {
                    eqPlace.setText(response.features[0]?.properties?.place.toString())
                } else{

                    if (eQaddress != null) {
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
    //date conversion function
    fun timeConverter(timeSinceEpoch: Long): LocalTime {

        val timeZone = TimeZone.of("UTC+1")
        val systemTZ = TimeZone.currentSystemDefault()
        val now: Instant = Clock.System.now()
        val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val tomorrow = now.plus(2, DateTimeUnit.DAY, systemTZ)
         Log.e("BELHAIMER", "la date d'aujourd'hui: $today et la date de demain: $tomorrow")

        //https://github.com/Kotlin/kotlinx-datetime
        val EQinstant = Instant.fromEpochMilliseconds(timeSinceEpoch.toLong())
        val EQtimeInSystemZone: LocalDateTime = EQinstant.toLocalDateTime(TimeZone.currentSystemDefault())//UTC
        val EQtimeInSystemZone2: LocalTime = EQinstant.toLocalDateTime(TimeZone.of(systemTZ.toString())).time


        Log.e("BELHAIMER", "la date et l'heure de l'evenement est : $EQtimeInSystemZone")
        Log.e("BELHAIMER", "l'heure de l'evenement est : $EQtimeInSystemZone2")


        return EQtimeInSystemZone2

    }

}