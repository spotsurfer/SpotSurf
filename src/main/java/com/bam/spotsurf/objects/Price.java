package com.bam.spotsurf.objects;

import java.io.Serializable;

/**
 * Created by bmerm on 7/28/2016.
 */
public class Price implements Serializable {

    double value;
    boolean isAllDay;
    String rate;

    public Price(double value, boolean isAllDay){
        this.value=value;
        this.isAllDay=isAllDay;
        if(isAllDay){
            rate="per day";
        }
        else{
            rate="per hour";
        }
    }

    public double getValue() {
        return value;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    @Override
    public String toString(){
        String priceString="$"+value+" "+rate;
        if(priceString!=null) {
            return priceString;
        }
        return "NA";
    }
}
