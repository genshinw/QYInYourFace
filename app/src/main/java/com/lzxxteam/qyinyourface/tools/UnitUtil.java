package com.lzxxteam.qyinyourface.tools;

import android.content.Context;

/**
 * 像素单位管理器
 * Created by Elvis on 2015/6/11.
 */
public class UnitUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px( float dpValue) {
        final float scale = AppGlobalMgr.getAppResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = AppGlobalMgr.getAppResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
