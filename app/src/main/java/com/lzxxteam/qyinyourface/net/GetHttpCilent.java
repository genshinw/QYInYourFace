package com.lzxxteam.qyinyourface.net;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * Created by Elvis on 2015/5/30.
 */
public class GetHttpCilent extends BaseHttpCilent{


    /**
     * 构造函数传入context变量便于控制
     *
     * @param context
     */
    public GetHttpCilent(Context context) {
        super(context);
    }

    /**
     * 该方法由父类的execRequest执行，子类可以在这里对所有参数进行修改
     * @param client
     * @param URL
     * @param headers
     * @param responseHandler
     * @return
     */
    @Override
    public RequestHandle executeRequest(
            AsyncHttpClient client, String URL, RequestParams rps,Header[] headers, ResponseHandlerInterface responseHandler) {

        String getUrl = getServerUrl()+URL;

        Log.i(LOG_TAG, "GET " + getUrl + " ,Data: " +( rps==null?"":rps.toString()));

        //get方法默认是添加到url中
        return  client.get(context,getUrl,headers,rps,responseHandler);

    }

    @Override
    public String getServerUrl() {
        return PROTOCOL+URL_SERVER;
    }
}
