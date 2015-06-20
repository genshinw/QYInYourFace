package com.lzxxteam.qyinyourface.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lzxxteam.qyinyourface.tools.LBSHelper;
import com.tencent.mapsdk.raster.model.LatLng;

/**
 * Created by Elvis on 2015/6/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GymData {

    private int id;
    private double lat;
    private double lng;
    private String name;
    private String gymAddr;
    private String gymDesc;

    public GymData(){


    }
    public GymData(String name ,double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public GymData(String name, String gymAddr,String gymdesc) {
        this.name = name;
        this.gymAddr = gymAddr;
        this.gymDesc = gymdesc;

    }


    public int getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public String getGymDesc() {
        return gymDesc;
    }
    @JsonProperty("tel")
    public void setGymDesc(String gymDesc) {
        this.gymDesc = gymDesc;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("cname")
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

    public String getGymAddr() {
        return gymAddr;
    }

    @JsonProperty("addr")
    public void setGymAddr(String gymAddr) {
        this.gymAddr = gymAddr;
    }
}
