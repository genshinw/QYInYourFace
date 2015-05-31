package com.lzxxteam.qyinyourface.activities;

import android.os.Bundle;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.ui.UserCentreViewControl;

/**
 * Created by Elvis on 2015/5/31.
 */
public class UserCentreAty extends BaseMainAty{

    private UserCentreViewControl ucvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("个人中心");
        menu3.setImageResource(R.drawable.m4_press);
        ucvc = new UserCentreViewControl(this);
        addToMainContainer(ucvc.getUserCentreView());

        ucvc.getDataFromNet();

    }
}
