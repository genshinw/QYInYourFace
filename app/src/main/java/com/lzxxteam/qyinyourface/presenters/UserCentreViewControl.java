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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.GymData;
import com.lzxxteam.qyinyourface.model.NetPackData;
import com.lzxxteam.qyinyourface.model.UserData;
import com.lzxxteam.qyinyourface.net.GetHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppConstantValue;
import com.lzxxteam.qyinyourface.tools.GetImageFromNet;
import com.lzxxteam.qyinyourface.tools.LogUtil;
import com.lzxxteam.qyinyourface.tools.WxHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.io.IOException;

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

        return userCentreView;
    }


    public void getDataFromNet(int userId){

        new GetHttpCilent(context).execRequest("fight/getUser/"+userId,null, new BaseJsonHttpResponseHandler<NetPackData>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, NetPackData response) {
                if(response.getType()==NetPackData.TYPE_PERSONAL_DETAIl
                        && response.getStatus()==NetPackData.STATUS_SUCC) {


                    setData(data);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, NetPackData errorResponse) {

            }

            @Override
            protected NetPackData parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                ObjectMapper mapper = new ObjectMapper();
                JsonParser jp = new JsonFactory().createParser(rawJsonData);
                jp.nextToken();//跳过{

                NetPackData netPackData = null;

                //刷新则要将数据清空
                while (jp.nextToken() != JsonToken.END_OBJECT) {

                    String fieldName = jp.getCurrentName();

                    jp.nextToken();//进入到键所对应的值的对象中

                    if (fieldName.equals("data")) {
                        jp.nextToken();
                        data = mapper.readValue(jp, UserData.class);


                    } else  if (fieldName.equals("head")) {
                        netPackData = mapper.readValue(jp, NetPackData.class);
                    }
                    else {
                        throw new IOException("Unrecognized field '"+fieldName+"'");
                    }
                }
                jp.close();


                if(netPackData!=null){
                    netPackData.setData(data);
                }
                return netPackData;
            }
        });

    }

    public void setData(UserData data) {
        this.data = data;

        usernNameText.setText(data.getUserName());
        if(data.getTeam().equals(""))
            userTeamText.setText("no team");
        else
            userTeamText.setText(data.getTeam());


        userCityText.setText(data.getUserAddr());
        userAgeText.setText(data.getUserAge()+"");
        userHightText.setText(data.getHeight()+"");
        userWeightText.setText(data.getWeight()+"");
        userGameTimesText.setText(data.getUserGameTimes()+"");

        GetImageFromNet.setProfileToImageView(data.getUserId()+"u.png",userProfileImage);

    }
}
