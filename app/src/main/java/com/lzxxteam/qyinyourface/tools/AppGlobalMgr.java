package com.lzxxteam.qyinyourface.tools;

import android.app.Application;
import android.content.res.Resources;

/**
 * Created by Elvis on 15/5/22.
 */
public class AppGlobalMgr extends Application{

    private  static AppGlobalMgr instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppGlobalMgr getAppContext(){

        return instance;
    }

    public static Resources getAppResources(){

        return instance.getResources();
    }

    public static String getResString(int stringId){

        return instance.getResources().getString(stringId);
    }
}
