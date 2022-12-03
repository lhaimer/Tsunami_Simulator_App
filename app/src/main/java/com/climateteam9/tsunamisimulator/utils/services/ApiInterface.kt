package com.climateteam9.tsunamisimulator.utils.services

import com.climateteam9.tsunamisimulator.utils.Constants
import com.climateteam9.tsunamisimulator.utils.data.datastructure
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/fdsnws/event/1/query.geojson")
    fun fetchFact(@Query("starttime") starttime: String,
                  @Query("endtime") endtime: String,
                  @Query("latitude") latitude: Double,
                  @Query("longitude") longitude: Double,
                  @Query("maxradiuskm") maxradiuskm: Int,
                  @Query("limit") limit: Int,
                  @Query("orderby") orderby: String

      ) : Call<datastructure>

    companion object {
        fun create() : ApiInterface {

            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.URLBASE)
                .build().create(ApiInterface::class.java)
        }
    }
}
