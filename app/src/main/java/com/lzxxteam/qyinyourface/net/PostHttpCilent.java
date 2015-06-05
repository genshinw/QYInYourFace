package com.lzxxteam.qyinyourface.net;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Elvis on 2015/5/30.
 */
public class PostHttpCilent extends BaseHttpCilent {


    private HttpEntity entity;

    private RequestParams requestParm;
    private String requestType;

    /**
     * 构造函数传入context变量便于控制
     *
     * @param context
     */
    public PostHttpCilent(Context context) {
        super(context);
    }

    @Override
    public RequestHandle executeRequest(AsyncHttpClient client, String URL, Header[] headers, ResponseHandlerInterface responseHandler) {

        if(requestParm==null){
            Log.e(LOG_TAG,"You must set RequetsParam first");
            return null;
        }
        String postURl = getServerUrl()+URL;
        Log.i(LOG_TAG, "POST To " + postURl + " ,Data: " + requestParm.toString());
        return  client.post(context,postURl,headers,requestParm,requestType,responseHandler);
    }


    public void setRequestEnity(String bodyText) {

        if (null != bodyText) {

            try {
                 entity = new StringEntity(bodyText);

            } catch (UnsupportedEncodingException e) {

                Log.e(LOG_TAG,"the bodyText cannot change to enity");
            }
        }

    }


    @Override
    public String getServerUrl() {
        return PROTOCOL+URL_SERVER;
    }

    public void setRequestParm(RequestParams requestParm,boolean isJson) {
        if(isJson) {
            requestType = RequestParams.APPLICATION_JSON;
            requestParm.setUseJsonStreamer(true);
        }
        else {
            requestType = RequestParams.APPLICATION_OCTET_STREAM;
        }
        this.requestParm = requestParm;
    }
}
