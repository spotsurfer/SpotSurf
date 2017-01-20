package com.bam.spotsurf.objects;

/**
 * Created by bmerm on 10/27/2016.
 */
public class SpotCard {
    private String address;
    private String status;
    private int imageId;

    public SpotCard(String address, String status, int imageId){
        this.address=address;
        this.status=status;
        this.imageId=imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }
}
