package com.lzxxteam.qyinyourface.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.LogMsgUtil;
import com.lzxxteam.qyinyourface.ui.UserCentreViewControl;

/**
 * Created by Elvis on 2015/5/31.
 */
public class UserCentreFgmt extends Fragment {

    private UserCentreViewControl ucvc;
    private Activity atyToAttach;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        atyToAttach = activity;
        if(ucvc==null)
            ucvc = new UserCentreViewControl(atyToAttach);
        

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(atyToAttach instanceof ActionBarActivity) {
            ActionBar actionBar = ((ActionBarActivity) atyToAttach).getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_base);
            ((TextView)actionBar.getCustomView().findViewById(R.id.id_tv_bar_name))
            .setText(R.string.fgmt_name_user_centre);

        }else{
            LogMsgUtil.e("Fragment's activity not actionBarActivity");
        }

        
        return ucvc.getUserCentreView();

    }

    @Override
    public void onStart() {
        super.onStart();

        ucvc.getDataFromNet();
    }


}
