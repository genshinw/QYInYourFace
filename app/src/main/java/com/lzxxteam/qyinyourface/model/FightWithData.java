package com.lzxxteam.qyinyourface.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lzxxteam.qyinyourface.R;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Elvis on 15/5/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FightWithData {

    public static int minId;
    private int id;
    private String[] fightSpace;
    private long[] fightTime;
    private String userName;


    @JsonProperty("name")
    public void setUserName(String userName) {
        this.userName = userName;
    }


    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;

    }

    @JsonProperty("time")
    public void setFightTime(long[] fightTime)
    {
        this.fightTime = fightTime;

    }

    @JsonProperty("court")
    public void setFightSpace(String[] fightSpace) {
        this.fightSpace = fightSpace;
    }


    public long[] getFightTime() {
        return fightTime;
    }

    public String getFightTimeStr()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
        sdf.applyPattern("MM-dd");

        String fightTimeString = null;
        for(int i=0;i<fightTime.length;i++) {
            if(i==0)
                fightTimeString = sdf.format(fightTime[i]);
            else
                fightTimeString = "/"+fightTimeString;
        }

        return fightTimeString;
    }

    public String[] getFightSpace() {
        return fightSpace;
    }


    public String getFightSpaceStr() {
        String fightSpaceStr="";
        for (int i = 0; i < fightSpace.length; i++) {
            if(i==0)
                fightSpaceStr+=fightSpace[i];
            else
                fightSpaceStr="/"+fightSpaceStr;
        }
        return fightSpaceStr;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }
}
