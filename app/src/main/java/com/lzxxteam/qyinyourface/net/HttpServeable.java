package com.lzxxteam.qyinyourface.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

/**
 * Created by Elvis on 2015/5/30.
 */
public interface HttpServeable {

    /**
     * 子类可以基于这一层的例如prePostRequest的封装
     * @param client
     * @param httpContext
     * @param uriRequest
     * @param contentType
     * @param responseHandler
     * @param context
     * @return
     */
    AsyncHttpRequest getHttpRequest
            (DefaultHttpClient client,
             HttpContext httpContext,
             HttpUriRequest uriRequest,
             String contentType,
             ResponseHandlerInterface responseHandler,
             Context context);

    RequestHandle executeRequest
            (AsyncHttpClient client,
             String URL,
             RequestParams rps,
             Header[] headers,
             ResponseHandlerInterface responseHandler);

    String getServerUrl();
}
