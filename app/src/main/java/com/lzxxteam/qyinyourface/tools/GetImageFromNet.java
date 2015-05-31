package com.lzxxteam.qyinyourface.tools;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Elvis on 2015/5/31.
 */
public class GetImageFromNet {


    private static final String URL_SERVER = "http://"+AppConstantValue.URL_SERVER;


    static{
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder
                (AppGlobalMgr.getAppContext()).build();


        ImageLoader.getInstance().init(config);
    }
    public static void setToImageView(String url,ImageView iv){

        ImageLoader.getInstance().displayImage(URL_SERVER+url,iv);
    }
    public static void setProfileToImageView(String url,ImageView iv){

        ImageLoader.getInstance().displayImage(URL_SERVER+"profile/"+url,iv);
    }
}
