package com.lzxxteam.qyinyourface.model;

import java.util.ArrayList;

/**
 * Created by Elvis on 15/5/23.
 */
public class DistrictData {

    ArrayList<String> districts;

    public DistrictData(){

    }

    public ArrayList<String> getDistricts() {
        if(districts==null)
            districts = new ArrayList<String>();

        districts.add("罗湖区");
        districts.add("南山区");
        districts.add("福田区");
        districts.add("盐田区");
        districts.add("宝安区");
        districts.add("龙岗区");


        return districts;
    }
}
