package com.bam.spotsurf.objects;

import java.util.List;

/**
 * Created by bmerm on 7/27/2016.
 */
public class PostedHistory {

    List <Spot> postHistory;

    public List <Spot> getPostHistory(){
        return postHistory;
    }

    public void addPost(Spot newSpot){
        postHistory.add(newSpot);
    }

    public void removeLastpost(){
        postHistory.remove(postHistory.size()-1);
    }


}
