package com.bam.spotsurf.objects;

import java.io.Serializable;

/**
 * Created by bmerm on 7/27/2016.
 */
public class Time implements Serializable{

    int hour;
    boolean isAllDay, isAM;
    String amOrPm;

    public Time(int hour, boolean isAM){
        this.hour= hour;
        this.isAM=isAM;
        if(isAM)
            amOrPm="AM";
        else
            amOrPm="PM";

    }

    public int getHour(){
        return hour;
    }

    public boolean isAM() {
        return isAM;
    }

    /*public int convertToStandardTime (int hour){
        if(hour>=12){
            amOrPm="PM";
            if(hour>12) {
                hour = hour-12;
            }
        }
        else{
            if(hour==0){
                hour=12;
            }
            amOrPm="AM";
        }
        return hour;

    }*/

    @Override
    public String toString(){
        String timeString;
        if(isAllDay){
            timeString="NA";
        }
        else{
            timeString = hour + " " + amOrPm;
        }
        return timeString;
    }
}
