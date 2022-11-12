package com.climateteam9.tsunamisimulator.Utils

class Constants {
companion object {


    //tsunami Japon

    val JapanWaveSpeed = 1 //meter per second in the sea
    val JapanTsunamiWaveHeight = 30 // to 41 meters
    val JapanTsunamiInLand = 10000     //m inland
    val MagnitudeEarthquakeJapon = 9.1 //earthquake
    val EarthquakeDepthJapon = 49000    //meters

    //tsunami Indinisia

     val IndonisiaWaveSpeed = 1  //meter per second in the sea
     val IndonisiaWaveHeight = 15
     val MagnitudeEarthquakeIndianOcean = 9.3  //earthquake magnitude
     val EarthquakeDepthIndianOcean = 30000 //meters
    const val NotSafeMSG = "you are not safe"
    const val SafeMSG = "you have just  seconds to move"


}
}