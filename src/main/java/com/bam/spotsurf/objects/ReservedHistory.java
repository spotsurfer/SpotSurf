package com.bam.spotsurf.objects;

import java.util.List;

/**
 * Created by bmerm on 7/27/2016.
 */
public class ReservedHistory {



    List<Spot> rentHistory;

    public List <Spot> getRentHistory(){
        return rentHistory;
    }

    public void addRent(Spot newSpot){
        rentHistory.add(newSpot);
    }

    public void removeLastRent(){
        rentHistory.remove(rentHistory.size()-1);
    }

}
