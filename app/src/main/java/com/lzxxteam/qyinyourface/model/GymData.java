package com.lzxxteam.qyinyourface.model;

import com.tencent.mapsdk.raster.model.LatLng;

/**
 * Created by Elvis on 2015/6/16.
 */
public class GymData {

    private double lat;
    private double lng;
    private String name;
    public GymData(String name ,double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LatLng getLatLng(){
        return  new LatLng(lat,lng );
    }
}
