package com.lzxxteam.qyinyourface.net;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;

/**
 * Created by Elvis on 2015/6/5.
 */
public class PutHttpCilent extends BaseHttpCilent{


    /**
     * 构造函数传入context变量便于控制
     *
     * @param context
     */
    public PutHttpCilent(Context context) {
        super(context);
    }

    @Override
    public RequestHandle executeRequest
            (AsyncHttpClient client, String URL,
             RequestParams rps,Header[] headers, ResponseHandlerInterface responseHandler) {

        if(rps==null){
            Log.e(LOG_TAG,"You must set requestParams first");
            return  null;
        }
        String putUrl = getServerUrl()+URL;
        Log.i(LOG_TAG, "PUT to " + putUrl + " data: " + rps.toString());
        return client.put(context,putUrl,rps,responseHandler);
    }



    @Override
    public String getServerUrl() {
        return PROTOCOL_HTTP+URL_SERVER;
    }
}
