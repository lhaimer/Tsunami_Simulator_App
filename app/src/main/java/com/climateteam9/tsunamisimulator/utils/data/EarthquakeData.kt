package com.climateteam9.tsunamisimulator.utils.data

import java.util.ArrayList

data class EarthquakeData(
    val Address:String ="",
    val Magnitude:Double=0.0,
    val DetectionTime:Int=0,
    val IsTsunami:Int=0,
    val coordinates: MutableList<Double> = mutableListOf(0.0, 0.0, 0.0)
)
