package com.lzxxteam.qyinyourface.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
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
            AsyncHttpClient client, String URL, Header[] headers, ResponseHandlerInterface responseHandler) {

        return  client.get(context,URL,headers,null,responseHandler);

    }
}
