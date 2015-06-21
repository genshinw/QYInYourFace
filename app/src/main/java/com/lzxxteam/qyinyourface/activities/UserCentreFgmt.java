package com.lzxxteam.qyinyourface.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.net.UserSession;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.presenters.UserCentreViewControl;

import java.lang.reflect.Field;

/**
 * Created by Elvis on 2015/5/31.
 */
public class UserCentreFgmt extends BaseFgmt {

    private UserCentreViewControl ucvc;
    private View userCentreView;
    private ViewGroup nologinView,hasloginView;
    private boolean isGetData = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(ucvc==null)
            ucvc = new UserCentreViewControl(atyToAttach);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setActionBarTitle(AppGlobalMgr.getResString(R.string.fgmt_name_user_centre));
        if(userCentreView==null)
            userCentreView = ucvc.getUserCentreView();

        nologinView =(ViewGroup) userCentreView.findViewById(R.id.ll_user_centre_no_login);
        nologinView.findViewById(R.id.id_btn_user_centre_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(atyToAttach, LoginAty.class);
                startActivity(intent);
            }
        });
        hasloginView = (ViewGroup)userCentreView.findViewById(R.id.ll_user_centre_has_login);

        return userCentreView;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(UserSession.getUserId()==-1){
            if(nologinView.getVisibility()==View.GONE)
                nologinView.setVisibility(View.VISIBLE);
            if(hasloginView.getVisibility()==View.VISIBLE)
                hasloginView.setVisibility(View.GONE);
        }else{
            if(hasloginView.getVisibility()==View.GONE)
                hasloginView.setVisibility(View.VISIBLE);

            if(nologinView.getVisibility()==View.VISIBLE)
                nologinView.setVisibility(View.GONE);


            if(!isGetData)
                ucvc.getDataFromNet(UserSession.getUserId());
        }
    }
}
