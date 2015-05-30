package com.lzxxteam.qyinyourface.ui;

import android.graphics.drawable.Drawable;

/**
 * Created by Elvis on 15/5/22.
 */
public class UserBaseData {

    public Drawable userPortrait;
    public String userName;
    public String userPortraitUtl;

    public UserBaseData(Drawable userPortrait,String userName){

        this.userName = userName;
        this.userPortrait = userPortrait;

    }

    public UserBaseData(){

    }
    public Drawable getUserPortrait() {
        return userPortrait;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPortraitUtl() {
        return userPortraitUtl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPortraitUtl(String userPortraitUtl) {
        this.userPortraitUtl = userPortraitUtl;
    }
}
