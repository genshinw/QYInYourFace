package com.lzxxteam.qyinyourface.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.presenters.UserCentreViewControl;

import java.lang.reflect.Field;

/**
 * Created by Elvis on 2015/5/31.
 */
public class UserCentreFgmt extends BaseFgmt {

    private UserCentreViewControl ucvc;
    private View userCentreView;


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

        return userCentreView;

    }

    @Override
    public void onStart() {
        super.onStart();

    }


}
