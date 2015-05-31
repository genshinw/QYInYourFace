package com.lzxxteam.qyinyourface.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.UserData;
import com.lzxxteam.qyinyourface.net.GetHttpCilent;
import com.lzxxteam.qyinyourface.tools.GetImageFromNet;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

/**
 * Created by Elvis on 2015/5/31.
 */
public class UserCentreViewControl {


    private  ImageLoader imageLoader;
    private Context context;
    private View userCentreView;
    private UserData data;

    public UserCentreViewControl(Context context){

        this.context = context;
        imageLoader = ImageLoader.getInstance();

    }

    public View getUserCentreView() {

        userCentreView = LayoutInflater.from(context).inflate(R.layout.aty_user_centre,null);



        return userCentreView;
    }


    public void getDataFromNet(){

        new GetHttpCilent(context).execRequest("userData.html", new BaseJsonHttpResponseHandler<UserData>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserData response) {
                
                setData(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserData errorResponse) {

            }

            @Override
            protected UserData parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValues(new JsonFactory().createParser(rawJsonData), UserData.class).next();
            }
        });

    }

    public void setData(UserData data) {
        this.data = data;
        String[] userMsgArray = data.getUserMsgsArray();


        ((TextView)userCentreView.findViewById(R.id.id_tv_user_centre_name)).setText(userMsgArray[0]);
        ImageView peopleProfile = (ImageView) userCentreView.findViewById(R.id.id_iv_user_centre_profile);
        GetImageFromNet.setProfileToImageView(data.getUserId()+".png",peopleProfile);
        ViewGroup userMsgs = (ViewGroup) userCentreView.findViewById(R.id.id_ll_user_centre_msg);

        for(int i=1;i<=6;i++){
            TextView tvUserMsg = (TextView) userMsgs.getChildAt(i);
            tvUserMsg.setText(userMsgArray[i]);
        }

    }
}
