package com.bam.spotsurf.application;




import com.google.android.gms.maps.model.Marker;
import com.bam.spotsurf.objects.Price;
import com.bam.spotsurf.objects.Spot;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.amazonaws.mobile.AWSMobileClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores global application state
 */
public class Application extends MultiDexApplication {

    private List<Spot> grandSpotList;
    private List<Spot> currentSpotSList, currentSpotLList, pastSpotSList, pastSpotLList;
    private List<Marker> spotMarkerList;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplication();

    }

    private void initializeApplication(){
        // Initialize the AWS Mobile Client
        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());

        grandSpotList =new ArrayList<Spot>();
        Spot a=new Spot("1 Heron Court Northport NY",false,false);
        a.setPrice(new Price(20,false));
        Spot b=new Spot("17 Bobolink Lane Northport NY",false,false);
        b.setPrice(new Price(20,false));
        Spot c=new Spot("1 Gilder Court Northport NY",false,false);
        c.setPrice(new Price(20, false));
        Spot d=new Spot("15 Gun Club Road Fort Salonga NY",false,false);
        d.setPrice(new Price(20,false));
        Spot e=new Spot("5 Stacey Lane East Northport NY",false,false);
        e.setPrice(new Price(20,false));
        grandSpotList.add(a);
        grandSpotList.add(b);
        grandSpotList.add(c);
        grandSpotList.add(d);
        grandSpotList.add(e);
        currentSpotLList= new ArrayList<Spot>();
        currentSpotSList= new ArrayList<Spot>();
        currentSpotSList.add(a);
        currentSpotSList.add(b);
        currentSpotSList.add(c);
        currentSpotSList.add(d);
        currentSpotSList.add(e);
        pastSpotSList= new ArrayList<Spot>();
        pastSpotLList= new ArrayList<Spot>();
        spotMarkerList = new ArrayList<Marker>();
    }


    public List<Spot> getCurrentSpotLList() {
        return currentSpotLList;
    }

    public List<Spot> getCurrentSpotSList() {
        return currentSpotSList;
    }

    public List<Spot> getPastSpotLList() {
        return pastSpotLList;
    }

    public List<Spot> getPastSpotSList() {
        return pastSpotSList;
    }

    public List<Spot> getGrandSpotList() {
        return grandSpotList;
    }

    public List<Marker> getSpotMarkerList() {
        return spotMarkerList;
    }
}
