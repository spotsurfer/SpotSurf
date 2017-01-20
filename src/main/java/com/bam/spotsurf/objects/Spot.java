package com.bam.spotsurf.objects;

import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;

/**
 * Created by bmerm on 7/27/2016.
 */
public class Spot implements Serializable{

    String address, car_size, spot_type, garageCode;
    boolean isCompact, isGarage;
    Date startDate, endDate;
    Time startTime, endTime;
    Price price;
    Marker marker;

    public Spot(String address, boolean isCompact, boolean isGarage){
        this.address=address;
        this.isCompact=isCompact;
        this.isGarage=isGarage;
        if(isCompact){
            car_size="Compact Only";
        }
        else{
            car_size="All";
        }
        if(isGarage){
            spot_type="Garage";
        }
        else{
            spot_type="Driveway";
            garageCode="NA";
        }


    }

    public void setGarageCode(String garageCode){
       this.garageCode= garageCode;
    }
    public String getGarageCode(){
        return garageCode;
    }
    public String getAddress(){
        return address;
    }


    public void setStartDate(Date date){
        this.startDate= date;
    }
    public void setEndDate(Date date){
        this.endDate= date;
    }
    public void setStartTime(Time time){
        this.startTime= time;
    }
    public void setEndTime(Time time){
        this.endTime= time;
    }

    public Date getStartDate(){
        return startDate;
    }
    public Date getEndDate(){
        return endDate;
    }
    public Time getStartTime(){
        return startTime;
    }
    public Time getEndTime(){
        return endTime;
    }

    public void setPrice(Price price){
        this.price=price;
    }
    public Price getPrice(){
        return price;
    }


    public void setMarker(Marker marker){
        this.marker=marker;
    }
    public Marker getMarker(){
        return marker;
    }

    public boolean isCompact(){return isCompact;}

    public boolean isGarage(){return isGarage;}

    @Override
    public String toString() {

            String spotSummary = "Address:    " + address +
                    "\nCar Size:    " + car_size +
                    "\nSpot Type:   " + spot_type +
                    "\nGarage Code: " + garageCode +
                    "\nStart Date:  " + startDate +
                    "\nEnd Date:    " + endDate +
                    "\nStart Time:  " + startTime +
                    "\nEnd Time:    " + endTime +
                    "\nPrice:       " + price;
            return spotSummary;

    }

}
