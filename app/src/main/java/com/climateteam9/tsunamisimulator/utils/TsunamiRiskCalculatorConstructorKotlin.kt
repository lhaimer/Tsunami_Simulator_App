package com.climateteam9.tsunamisimulator.utils

import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import android.provider.Settings


class TsunamiRiskCalculatorConstructorKotlin(
    /**
     * Important variables Tsunami risk calculation
     */
    var SeaDepth // Sea Dept meters
    : Double, // Earthquake Magnitude
    var EarthQuakeMag: Float, OceanProx: Boolean
) {
    var OceanProx // User Proximity to ocean
            : Boolean

    //float QuaFreq; // Earthquake Frequency
    //Tsunami risk constructor
    init {
        //
        EarthQuakeMag = EarthQuakeMag
        this.OceanProx = OceanProx
    }

    fun ProxCalc(): Boolean {
        if (OceanProx == false) {
            println("There is also low risk of Tsunami because you are far from the ocean")
        } else if (true.also { OceanProx = it }) {
            println("There is also some risk of Tsunami because you are close to the ocean")
        }
        return true
    }

    //Speed in KM/Hr.
    fun SpeedCalculator(): Double {
        return Math.sqrt(9.8 * SeaDepth)
    }

    //Height in KM
    fun HeightCalculator(): Double {
        return 1 / Math.sqrt(SeaDepth)
    }

    //Power in Joules
    fun PowerCalculator(): Double {
        val Power = Math.sqrt(EarthQuakeMag.toDouble())
        if (EarthQuakeMag > 0 && EarthQuakeMag <= 4) {
            println("There has been an earthquake near you and you therefore face a very low risk of Tsunami!")
        } else if (EarthQuakeMag > 4 && EarthQuakeMag <= 5) {
            println("There has been an earthquake near you and you therefore face a low risk of Tsunami!")
        } else if (EarthQuakeMag > 5 && EarthQuakeMag <= 6) {
            println("There has been an earthquake near you and you therefore face a medium risk of Tsunami!")
        } else if (EarthQuakeMag > 6 && EarthQuakeMag <= 7) {
            println("There has been an earthquake near you and you therefore face a high risk of Tsunami!")
        } else if (EarthQuakeMag > 8 && EarthQuakeMag <= 10) {
            println("There has been an earthquake near you and you therefore face a very high risk of Tsunami. Move to to a safer location!")
        }
        return Power
    }

    fun Notify ():String {
         //user proximity to the coast
        var msg : String =""
        if (OceanProx == false) {

            msg = "There is also low risk of Tsunami because you are far from the ocean"

        } else if (true.also { OceanProx = it }) {

            //"There is also some risk of Tsunami because you are close to the ocean"
            if (EarthQuakeMag > 0 && EarthQuakeMag <= 4) {
                msg = "There has been an earthquake near you and you therefore face a very low risk of Tsunami!"
            } else if (EarthQuakeMag > 4 && EarthQuakeMag <= 5) {
                msg = "There has been an earthquake near you and you therefore face a low risk of Tsunami!"
            } else if (EarthQuakeMag > 5 && EarthQuakeMag <= 6) {
                msg = "There has been an earthquake near you and you therefore face a medium risk of Tsunami!"
            } else if (EarthQuakeMag > 6 && EarthQuakeMag <= 7) {
                msg = "There has been an earthquake near you and you therefore face a high risk of Tsunami!"
            } else if (EarthQuakeMag > 8 && EarthQuakeMag <= 10) {
                msg = "There has been an earthquake near you and you therefore face a very high risk of Tsunami. Move to to a safer location!"
                

            }
        }

            val T = TsunamiRiskCalculatorConstructorKotlin(1200.0, 5.0F, false)
            val WaveSpeedValue = T.SpeedCalculator()
            val WaveHeightValue = T.HeightCalculator()
            val WavePowerValue = T.PowerCalculator()
            val ProxValue = T.ProxCalc()
            val WaveRiskValue =
                Math.round(WaveSpeedValue + WaveHeightValue + WavePowerValue).toDouble()
            println("is $ProxValue")
            println("Your Tsunami risk percentage is $WaveRiskValue%")

        return msg
    }

    companion object {
        //    public double RiskCalculator() {
        //        double Risk = SeaDepth + EarthQuakeMag + Prox;
        //        return Risk;
        //    }
        //Main method to be called and instantiated for Tsunami risk calculation
        //@JvmStatic
        //fun main(args: Array<String>)

    }
}