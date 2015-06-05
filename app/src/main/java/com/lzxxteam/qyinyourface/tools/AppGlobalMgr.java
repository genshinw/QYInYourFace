package com.lzxxteam.qyinyourface.tools;

import android.app.Application;
import android.content.res.Resources;

import com.cengalabs.flatui.FlatUI;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Elvis on 15/5/22.
 */
public class AppGlobalMgr extends Application{

    private  static AppGlobalMgr instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化FLATUI主题
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.CANDY);

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
