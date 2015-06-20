package com.lzxxteam.qyinyourface.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lzxxteam.qyinyourface.R;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lzxxteam.qyinyourface.tools.LogUtil;

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
    private String[] fightTime;
    private String[] userNames;
    private int[] userids;

    @JsonProperty("name")
    public void setUserName(String[] userNames) {
        this.userNames = userNames;
    }

    public int[] getUserids() {
        return userids;
    }
    public int getFaqiUserId(){
        if(userids.length>0)
            return userids[0];

        return 0;
    }
    @JsonProperty("userids")
    public void setUserids(int[] userids) {
        this.userids = userids;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;

    }

    @JsonProperty("choseTime")
    public void setFightTime(String[] fightTime)
    {
        this.fightTime = fightTime;

    }

    @JsonProperty("cnames")
    public void setFightSpace(String[] fightSpace) {
        this.fightSpace = fightSpace;
    }


    public String[] getFightTime() {
        return fightTime;
    }

    public String getFightDateStr()
    {

       String fightDateStr = "";
        if(fightTime==null)
            return fightDateStr;

        for(int i=0;i<fightTime.length;i++) {
            String[] fightTimeDatas = fightTime[i].split("-");
            fightDateStr+=fightTimeDatas[1]+"月"+fightTimeDatas[2]+"日";

            if(i!=0)
                fightDateStr = "/"+fightDateStr;
        }

        return  fightDateStr;
    }
    public String getFightTimeStr(){
        String fightTimeStr = "";
        if(fightTime==null)
            return fightTimeStr;

        for(int i=0;i<fightTime.length;i++) {
            String[] fightTimeDatas = fightTime[i].split("-");
            fightTimeStr+=TimeListData.getTimeScope(Integer.valueOf(fightTimeDatas[3]));

            if(i!=0)
                fightTimeStr = "/"+fightTimeStr;
        }
        return fightTimeStr;

    }
    public String[] getFightSpace() {
        return fightSpace;
    }


    public String getFightSpaceStr() {
        String fightSpaceStr="";

        if(fightSpace==null)
            return fightSpaceStr;


        for (int i = 0; i < fightSpace.length; i++) {
            if(i==0)
                fightSpaceStr+=fightSpace[i];
            else
                fightSpaceStr="/"+fightSpaceStr;
        }
        return fightSpaceStr;
    }

    public String[] getUserNames() {
        return userNames;
    }

    public String getFaqiUserName(){
        if(userNames.length>0)
            return userNames[0];
        else
            return "";
    }

    public int getId() {
        return id;
    }
}
