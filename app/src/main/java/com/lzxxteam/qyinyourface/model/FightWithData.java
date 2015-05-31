package com.lzxxteam.qyinyourface.model;

import com.lzxxteam.qyinyourface.R;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithData {


    private String fightSpace;
    private String fightTime;
    private String userName;




    @JsonProperty("fight-time")
    public void setFightTime(String fightTime) {
        this.fightTime = fightTime;
    }

    @JsonProperty("fight-space")
    public void setFightSpace(String fightSpace) {
        this.fightSpace = fightSpace;
    }
    @JsonProperty("user-name")
    public void setUserName(String userName) {
        this.userName = userName;
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
}
