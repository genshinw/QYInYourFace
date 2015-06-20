package com.lzxxteam.qyinyourface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.net.PostHttpCilent;
import com.lzxxteam.qyinyourface.net.UserSession;
import com.lzxxteam.qyinyourface.tools.GetImageFromNet;

import org.apache.http.Header;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Elvis on 2015/6/6.
 */
public class FightWithDetailAty extends BaseAty{

    private View btn_access_fight;
    private int fightWithDetailItemId;
    private PostHttpCilent postHttpCilent;
    private String userName;
    private int listtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aty_fight_with_detail);
        ViewGroup fightTimeViewGroup = (ViewGroup)
                LayoutInflater.from(this).inflate(R.layout.ll_detail_fight_with_item,null);

        TextView userNameView = (TextView)findViewById(R.id.id_tv_aty_fight_detail_user_name);

        fightWithDetailItemId = getIntent().getIntExtra("fightid",0);
        userName = getIntent().getStringExtra("userName");
        listtype = getIntent().getIntExtra("listtype", 0);
        postHttpCilent = new PostHttpCilent(this) ;

        TextView spaceTextView = (TextView) fightTimeViewGroup
                .findViewById(R.id.id_aty_fight_detail_item_space);

        TextView dateTextView = (TextView)fightTimeViewGroup
                .findViewById(R.id.id_aty_fight_detail_item_date);

        TextView timeTextView = (TextView)fightTimeViewGroup
                .findViewById(R.id.id_aty_fight_detail_item_time);
        ((LinearLayout)findViewById(R.id.id_ll_aty_fight_detail)).addView(fightTimeViewGroup);

        if(listtype==1) {
            GetImageFromNet.setProfileToImageView(getIntent().getIntExtra("userid", 0) + "u.png",
                    (ImageView) findViewById(R.id.id_aty_fight_detail_profile));
        }else{
            GetImageFromNet.setProfileToImageView(getIntent().getIntExtra("userid", 0) + "t.png",
                    (ImageView) findViewById(R.id.id_aty_fight_detail_profile));
        }

        spaceTextView.setText(getIntent().getStringExtra("fightArea"));
        dateTextView.setText(getIntent().getStringExtra("fightDate"));
        timeTextView.setText(getIntent().getStringExtra("fightTime"));
        userNameView.setText(userName);

        btn_access_fight = findViewById(R.id.id_aty_fight_detail_access);
        btn_access_fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(UserSession.getUserId()==-1){

                    new SweetAlertDialog(FightWithDetailAty.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提醒")
                            .setContentText("你还未登陆，请先登录")
                            .setConfirmText("login")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(FightWithDetailAty.this, LoginAty.class);
                                    startActivity(intent);
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                    return;
                }
                new SweetAlertDialog(FightWithDetailAty.this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提醒")
                        .setContentText("是否和" + userName + "球队约战")
                        .setConfirmText("约起")
                        .setCancelText("不约")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.setContentText("请求已发送")
                                        .setConfirmText("确定")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                                setContentView(R.layout.aty_fight_with_detail2);
                                            }
                                        })
                                        .showCancelButton(false)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        }).show();
            }
        });
    }

    private void sendFightData(){

        RequestParams rps = new RequestParams();
        rps.put("listtype",listtype);
        rps.put("name",userName);
        rps.put("fightID",fightWithDetailItemId);
        postHttpCilent.execRequest("", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }
}
