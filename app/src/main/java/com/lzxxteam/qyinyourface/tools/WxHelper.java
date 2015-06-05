package com.lzxxteam.qyinyourface.tools;

import android.content.Context;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Elvis on 2015/6/5.
 */
public class WxHelper {

    public final String WX_APP_ID = AppConstantValue.WX_APP_ID;
    private IWXAPI api;

    public WxHelper(Context context){
        api = WXAPIFactory.createWXAPI(context, AppConstantValue.WX_APP_ID, true);
        api.registerApp(AppConstantValue.WX_APP_ID);
    }


    public void LoginFromWx(){
         SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }
}
