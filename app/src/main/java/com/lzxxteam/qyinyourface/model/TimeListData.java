package com.lzxxteam.qyinyourface.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elvis on 2015/6/20.
 */
public class TimeListData {

    private static HashMap<Integer ,String> timeMap;


    public static String getTimeScope(int num){
        if(timeMap==null){
             timeMap= new HashMap<Integer,String>();
            timeMap.put(1,"8:00-10:00");
            timeMap.put(2,"10:00-12:00");
            timeMap.put(3,"14:00-16:00");
            timeMap.put(4,"16:00-18:00");
            timeMap.put(5,"19:00-20:00");
            timeMap.put(6,"20:00-22:00");
        }
        return timeMap.get(num);
    }




}
