package com.lzxxteam.qyinyourface.tools;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * 引用tencent Bugly的调试方案
 * Created by Elvis on 2015/6/18.
 */
public class BuglyHelper {

    private static final String BUGLY_APP_ID = "900004472";

    //之后配置符号表需要
    private static final String BUGLY_APP_KEY = "bwiowHrHDmxxsWxo";

    private static final boolean IS_DEBUG = AppGlobalMgr.APP_IS_DEBUG;

    public static void initBugly(Context context){

        CrashReport.initCrashReport(context, BUGLY_APP_ID, IS_DEBUG);  //初始化SDK
    }

    public static void postCatchedExecption(Throwable thr){

        LogUtil.e(thr.getMessage());
        CrashReport.postCatchedException(thr);
    }


}
