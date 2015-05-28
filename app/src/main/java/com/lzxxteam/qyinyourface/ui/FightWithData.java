package com.lzxxteam.qyinyourface.ui;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithData {


    private UserBaseData userData;
    private String space;
    private String fightTime;


    public FightWithData(UserBaseData userBaseData ,String space ,String fightTime){

        this.userData = userBaseData;
        this.space = space;
        this.fightTime = fightTime;

    }

    public String getFightTime() {
        return fightTime;
    }

    public String getSpace() {
        return space;
    }

    public UserBaseData getUserData() {
        return userData;
    }


}
