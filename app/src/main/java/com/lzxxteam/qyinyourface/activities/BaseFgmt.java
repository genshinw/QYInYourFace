package com.lzxxteam.qyinyourface.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.LogUtil;

import java.lang.reflect.Field;

/**
 * Created by Elvis on 2015/6/11.
 */
public abstract class BaseFgmt extends Fragment{

    protected Activity atyToAttach;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        atyToAttach = activity;
    }

    protected  void setActionBarTitle(String actionBarTileName){
        if(atyToAttach instanceof ActionBarActivity) {
            ActionBar actionBar = ((ActionBarActivity) atyToAttach).getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar_base);
            ((TextView)actionBar.getCustomView().findViewById(R.id.id_tv_bar_name))
                    .setText(actionBarTileName);

        }else{
            LogUtil.e("Fragment's activity not actionBarActivity");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
