package com.lzxxteam.qyinyourface.activities;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.cengalabs.flatui.FlatUI;
import com.lzxxteam.qyinyourface.R;

/**
 * Created by Elvis on 2015/5/30.
 */
public abstract class BaseAty extends ActionBarActivity {

    public String LOG_TAG = getClass().getName().toString();
    protected  ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        //		隐藏logo
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(FlatUI.getActionBarDrawable(this,R.array.myredtheme,false));

        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
