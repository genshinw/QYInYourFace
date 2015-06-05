package com.lzxxteam.qyinyourface.model;

import com.lzxxteam.qyinyourface.R;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithData {

    public static int minId;
    private int id;
    private String fightSpace;
    private String fightTime;
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
    public void setFightTime(String fightTime) {
        this.fightTime = fightTime;
    }

    @JsonProperty("space")
    public void setFightSpace(String fightSpace) {
        this.fightSpace = fightSpace;
    }


    public String getFightTime() {
        return fightTime;
    }

    public String getFightSpace() {
        return fightSpace;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }
}
