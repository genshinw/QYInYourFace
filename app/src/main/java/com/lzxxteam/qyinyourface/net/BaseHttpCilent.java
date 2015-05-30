package com.lzxxteam.qyinyourface.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Elvis on 15/5/22.
 * 基于对loopj的 async-http-client项目中的sample样例封装的服务类
 */
public abstract class BaseHttpCilent implements  HttpServeable{

    protected static final String PROTOCOL_HTTP = "http://";
    protected static final String PROTOCOL_HTTPS = "https://";
    protected  Throwable throwable = new Throwable();
    protected  String LOG_TAG = getClass().getName()+throwable.getStackTrace()[0];

    /**
     *默认的是基于HTTP的服务
     */
    protected static String PROTOCOL = PROTOCOL_HTTP;
    /**
     * 用于处理请求的上下文
     */
    protected Context context;
    /**
     * 存储发出的java.lang.Stringhttp请求的handle，可用于撤销等操作
     */
    private  RequestHandle lastRequestHandle ;

    /**
     * 异步的httpCilent
     */
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient(){

        @Override
        protected AsyncHttpRequest newAsyncHttpRequest(
                DefaultHttpClient client,
                HttpContext httpContext,
                HttpUriRequest uriRequest,
                String contentType,
                ResponseHandlerInterface responseHandler,
                Context context)
        {
            AsyncHttpRequest httpRequest = getHttpRequest
                    (client,httpContext,uriRequest,contentType,responseHandler,context);


            return httpRequest==null ?
                    super.newAsyncHttpRequest
                            (client, httpContext, uriRequest, contentType, responseHandler, context)
                    : httpRequest;
        }
    };

    /**
     * 构造函数传入context变量便于控制
     * @param context
     */
    public  BaseHttpCilent(Context context){

        this.context = context;
    }

    @Override
    public AsyncHttpRequest getHttpRequest
            (DefaultHttpClient client,
             HttpContext httpContext,
             HttpUriRequest uriRequest,
             String contentType,
             ResponseHandlerInterface responseHandler,
             Context context)
    {

        return null;
    }

    public Context getContext() {
        return context;
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return asyncHttpClient;
    }


    protected Header[] getRequestHeaders(String url) {
        List<Header> headers = new ArrayList<Header>();
        String[] urlKeyValues = url.split("\\?");

        //没有header参数
        if (urlKeyValues.length == 1) {
            return null;
        }

        //假设有以问号分隔的参数
        String[] keyValues = urlKeyValues[1].split("&");
        for (String keyValue : keyValues) {
            try {
                int equalSignPos = keyValue.indexOf('=');
                if (1 > equalSignPos) {
                    throw new IllegalArgumentException("Wrong header format, may be 'Key=Value' only");
                }

                String headerName = keyValue.substring(0, equalSignPos).trim();
                String headerValue = keyValue.substring(1 + equalSignPos).trim();
                Log.d(LOG_TAG, String.format("Added header: [%s:%s]", headerName, headerValue));

                headers.add(new BasicHeader(headerName, headerValue));
            } catch (Throwable t) {
                Log.e(LOG_TAG, "Not a valid header line: " + keyValue, t);
            }
        }
        return headers.toArray(new Header[headers.size()]);
    }


    /**
     * 执行请求
     * @param url
     * @param responseHandlerInterface
     */
    public void execRequest(String url,ResponseHandlerInterface responseHandlerInterface) {

        lastRequestHandle = executeRequest(
                getAsyncHttpClient(),
                url,
                getRequestHeaders(url),
                responseHandlerInterface
        );
        if (lastRequestHandle==null) {
            Log.e(LOG_TAG,"request error");
        }
    }

    public static void getDataFromNet(final Context context){

        new AsyncHttpClient().get(context,"http://172.30.66.158/abc.html", null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(context,new String(responseBody),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });

    }

}
