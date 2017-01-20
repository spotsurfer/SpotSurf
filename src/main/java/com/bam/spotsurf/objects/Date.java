package com.bam.spotsurf.objects;

import java.io.Serializable;

/**
 * Created by bmerm on 7/27/2016.
 */
public class Date implements Serializable{

    int day, month, year;

    public Date(int day, int month, int year){
        this.day=day;
        this.month=month;
        this.year=year;

    }

    public int getDay(){
        return day;
    }
    public int getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }

    @Override
    public String toString() {
        String dateString = month + "/" + day + "/" + year;
        if (dateString != null) {
            return dateString;
        }
        return "NA";
    }

}
