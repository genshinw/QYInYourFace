package com.lzxxteam.qyinyourface.presenters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
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
import com.lzxxteam.qyinyourface.tools.AppConstantValue;
import com.lzxxteam.qyinyourface.tools.GetImageFromNet;
import com.lzxxteam.qyinyourface.tools.WxHelper;
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
    private TextView usernNameText,userTeamText,userAgeText,userCityText
            ,userHightText,userWeightText,userGameTimesText;
    private ImageView userProfileImage;

    public UserCentreViewControl(Context context){

        this.context = context;
        imageLoader = ImageLoader.getInstance();

    }

    public View getUserCentreView() {

        if(userCentreView==null) {
            userCentreView = LayoutInflater.from(context).inflate(R.layout.fgmt_user_centre, null);
            usernNameText =  ((TextView)userCentreView.findViewById(R.id.id_tv_user_centre_name));
            userTeamText = (TextView)userCentreView.findViewById(R.id.id_tv_user_centre_team);
            userAgeText = (TextView)userCentreView.findViewById(R.id.id_tv_user_centre_age);
            userCityText = (TextView)userCentreView.findViewById(R.id.id_tv_user_centre_city);
            userHightText = (TextView)userCentreView.findViewById(R.id.id_tv_user_centre_hight);
            userWeightText = (TextView)userCentreView.findViewById(R.id.id_tv_user_centre_weight);
            userGameTimesText = (TextView)userCentreView.findViewById(R.id.id_tv_user_centre_gametimes);
            userProfileImage = (ImageView) userCentreView.findViewById(R.id.id_iv_user_centre_profile);
        }
        userCentreView.findViewById(R.id.id_ll_aty_uc_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WxHelper.sendWebpageToWx(context);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromNet();
            }
        },300);
        return userCentreView;
    }


    public void getDataFromNet(){

        new GetHttpCilent(context).execRequest(AppConstantValue.URL_TEST_DIR+"testUserData.json",null, new BaseJsonHttpResponseHandler<UserData>() {
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

        usernNameText.setText(data.getUserName());
        userTeamText.setText(data.getTeam());
        userCityText.setText(data.getUserAddr());
        userAgeText.setText(data.getUserAge()+"");
        userHightText.setText(data.getHeight()+"");
        userWeightText.setText(data.getWeight()+"");
        userGameTimesText.setText(data.getUserGameTimes()+"");

        GetImageFromNet.setProfileToImageView(data.getUserId()+".png",userProfileImage);

    }
}
