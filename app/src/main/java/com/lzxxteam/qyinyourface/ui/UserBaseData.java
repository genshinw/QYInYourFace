package com.lzxxteam.qyinyourface.ui;

import android.graphics.drawable.Drawable;

/**
 * Created by Elvis on 15/5/22.
 */
public class UserBaseData {

    public Drawable userPortrait;
    public String userName;


    public UserBaseData(Drawable userPortrait,String userName){

        this.userName = userName;
        this.userPortrait = userPortrait;

    }


    public Drawable getUserPortrait() {
        return userPortrait;
    }

    public String getUserName() {
        return userName;
    }




}
