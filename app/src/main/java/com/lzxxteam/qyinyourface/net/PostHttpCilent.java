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

    //默认post的都是json
    private String requestType = RequestParams.APPLICATION_JSON;

    /**
     * 构造函数传入context变量便于控制
     *
     * @param context
     */
    public PostHttpCilent(Context context) {
        super(context);
    }

    @Override
    public RequestHandle executeRequest(AsyncHttpClient client, String URL,RequestParams rps, Header[] headers, ResponseHandlerInterface responseHandler) {


        String postURl = getServerUrl()+URL;
        Log.i(LOG_TAG, "POST To " + postURl + " ,Data: " + (rps==null?"":rps.toString()));

        if(requestType==RequestParams.APPLICATION_JSON)
            rps.setUseJsonStreamer(true);

        return  client.post(context,postURl,headers,rps,requestType,responseHandler);
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

    public void setRequestType(boolean isJson) {
        if(isJson) {
            requestType = RequestParams.APPLICATION_JSON;
        }
        else {
            requestType = RequestParams.APPLICATION_OCTET_STREAM;
        }
    }
}
