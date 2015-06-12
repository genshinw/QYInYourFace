package com.lzxxteam.qyinyourface.tools;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cengalabs.flatui.FlatUI;
import com.lzxxteam.qyinyourface.R;
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

    public static Drawable getResImg(int imgId){

        return instance.getResources().getDrawable(imgId);
    }

    public  static int getResColor(int colorId){

        return instance.getResources().getColor(colorId);
    }

    public static int getScreenWidth() {
        WindowManager manager = (WindowManager) instance
                .getSystemService(Context.WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
    //获取屏幕的高度
    public static int getScreenHeight() {
        WindowManager manager = (WindowManager) instance
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
