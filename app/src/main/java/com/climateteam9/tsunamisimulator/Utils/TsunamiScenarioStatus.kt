package com.climateteam9.tsunamisimulator.Utils

import android.util.Log

open class TsunamiScenarioStatus (
     var delayTime:Int,
     var userSpeed:Int,
     var userLocation:Int,
     var type: String,
     var initialDistance:Int,
     var power:Float
        )
{
    open fun getWaveArrivalTime():Int{
        var speed:Float
            if(type =="Japon"){
           speed = (Constants.JapanWaveSpeed*power)
        }else{speed = (Constants.IndonisiaWaveSpeed)*power}
        var time= (initialDistance/speed) //seconds
        return time.toInt()
    }
    open fun UserSafetyStatus():String {
        var message : String
        val arrivalTime=getWaveArrivalTime()
        Log.e("the tsunami parameters", "the wave will arrive in : $arrivalTime secondes")
        if(delayTime*60>arrivalTime){message= Constants.NotSafeMSG
    }else{
        val deltatime= arrivalTime -(delayTime*60)
        message =Constants.SafeMSG +"$deltatime seconds"}
        return message
    }
    open fun userIsfasterToTakeAvction():Boolean
    {
        val arrivalTime=getWaveArrivalTime()
        return delayTime>arrivalTime
    }



}