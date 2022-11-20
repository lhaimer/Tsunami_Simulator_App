package com.climateteam9.tsunamisimulator.utils.services

import android.content.Context
import android.util.Log
import com.climateteam9.tsunamisimulator.utils.data.datastructure
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthquakeDetectionService {


/*

    companion object {

        fun getdata(context:Context,StartTime:String,EndTime:String) {

            val service = ApiInterface.create()

            val call: Call<datastructure> = service.fetchFact("StartTime", "EndTime")
            call.enqueue(object : Callback<datastructure> {

                override fun onResponse(
                    call: Call<datastructure>,
                    response: Response<datastructure>
                ) {
                    val response = response.body()
                    Log.d("fitshdata, new try: ", response.toString())
                    response?.features?.get(0)?.properties?.place?.let {
                        Log.d(
                            "API DATA",
                            "Earthquake address: $it"
                        )
                    }
                    var Address = response?.features?.get(0)?.properties?.place.toString()

                    response?.features?.get(0)?.properties?.mag?.let {
                        Log.d(
                            "API DATA", "Earthquake magnitude: " +
                                    it.toString()
                        )
                    }
                    var Magnitude = response?.features?.get(0)?.properties?.mag!!

                    response?.features?.get(0)?.properties?.time?.let {
                        Log.d(
                            "API DATA", "Earthquake time: " +
                                    it.toString()
                        )
                    }
                    var DetectionTime = response?.features?.get(0)?.properties?.time?.toInt()!!
                    response?.features?.get(0)?.properties?.tsunami?.let {
                        Log.d(
                            "API DATA", "Tsunami alert level: " +
                                    it.toString()
                        )
                    }
                    var IsTsunami = response?.features?.get(0)?.properties?.tsunami!!
                    response?.features?.get(0)?.geometry?.coordinates.let {
                        Log.d(
                            "API DATA", "Earthquake location: " +
                                    it.toString()
                        )
                    }
                    var coordinates: MutableList<Double> = mutableListOf(0.0, 0.0, 0.0)
                    coordinates[0] = response?.features?.get(0)?.geometry?.coordinates?.get(0)!!
                    coordinates[1] = response?.features?.get(0)?.geometry?.coordinates?.get(1)!!
                    coordinates[2] = response?.features?.get(0)?.geometry?.coordinates?.get(2)!!




                }

                override fun onFailure(call: Call<datastructure>, t: Throwable) {
                    t.message?.let { Log.d("fitshdata, new try:", it) }
                }
            })



        }
    }*/
}