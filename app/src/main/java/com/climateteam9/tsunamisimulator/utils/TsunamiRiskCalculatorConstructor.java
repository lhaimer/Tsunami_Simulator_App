package com.climateteam9.tsunamisimulator.utils;

/**
 *
 * @author Olanrewaju Ajayi
 */
public class TsunamiRiskCalculatorConstructor {

    /**
     * Important variables Tsunami risk calculation
     */

    double SeaDepth; // Sea Dept
    float EarthQuakeMag; // Earthquake Magnitude
    boolean OceanProx; // User Proximity to ocean
    //float QuaFreq; // Earthquake Frequency

    //Tsunami risk constructor
    public TsunamiRiskCalculatorConstructor(double SeaDepth,float EarthQuakeMag,boolean OceanProx)
    {
        this.SeaDepth = SeaDepth; //
        this.EarthQuakeMag = EarthQuakeMag;
        this.OceanProx = OceanProx;
    }

    public boolean ProxCalc(){
        if (OceanProx==false){
            System.out.println("There is also low risk of Tsunami because you are far from the ocean");
        }
        else if(OceanProx=true){
            System.out.println("There is also some risk of Tsunami because you are close to the ocean");
        }
        return true;
    }
    //Speed in KM/Hr.
    public double SpeedCalculator(){
        double Speed = Math.sqrt(9.8*SeaDepth);
        return Speed;
    }

    //Height in KM
    public double HeightCalculator(){
        double Height = 1/Math.sqrt(SeaDepth);
        return Height;
    }

    //Power in Joules
    public double PowerCalculator(){
        double Power = Math.sqrt(EarthQuakeMag);

        if (EarthQuakeMag>0 && EarthQuakeMag<=4){
            System.out.println("There has been an earthquake near you and you therefore face a very low risk of Tsunami!");
        }
        else if(EarthQuakeMag>4 && EarthQuakeMag<=5){
            System.out.println("There has been an earthquake near you and you therefore face a low risk of Tsunami!");
        }
        else if(EarthQuakeMag>5 && EarthQuakeMag<=6){
            System.out.println("There has been an earthquake near you and you therefore face a medium risk of Tsunami!");
        }
        else if(EarthQuakeMag>6 && EarthQuakeMag<=7){
            System.out.println("There has been an earthquake near you and you therefore face a high risk of Tsunami!");
        }
        else if(EarthQuakeMag>8 && EarthQuakeMag<=10){
            System.out.println("There has been an earthquake near you and you therefore face a very high risk of Tsunami. Move to to a safer location!");
        }
        return Power;
    }




    //    public double RiskCalculator() {
//        double Risk = SeaDepth + EarthQuakeMag + Prox;
//        return Risk;
//    }
//Main method to be called and instantiated for Tsunami risk calculation
    public static void main(String[] args) {
        try {
            TsunamiRiskCalculatorConstructorKotlin T = new TsunamiRiskCalculatorConstructorKotlin(1200,5,false);
            double WaveSpeedValue = T.SpeedCalculator();
            double WaveHeightValue = T.HeightCalculator();
            double WavePowerValue = T.PowerCalculator();
            boolean ProxValue = T.ProxCalc();
            double WaveRiskValue = Math.round(WaveSpeedValue+WaveHeightValue+WavePowerValue);
            System.out.println("is " +ProxValue);
            System.out.println("Your Tsunami risk percentage is " + WaveRiskValue+"%");
        }
        catch (Exception ex) {
            ex.getMessage();
        }
    }
}
