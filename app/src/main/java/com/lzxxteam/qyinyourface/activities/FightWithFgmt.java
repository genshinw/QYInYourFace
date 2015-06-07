package com.lzxxteam.qyinyourface.activities;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.LogMsgUtil;
import com.lzxxteam.qyinyourface.ui.CitySelectorPopUp;
import com.lzxxteam.qyinyourface.ui.FightWithViewControler;
import com.lzxxteam.qyinyourface.ui.ViewAdapter;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FightWithFgmt extends Fragment {


    List<View> viewList;

    private ViewGroup fightWithView;


    private ViewPager viewPager;

    private ViewAdapter viewAdapter;
    private TencentLocationManager mLocationManager;
    private Listener_Location lisloc;
    private FightWithViewControler fvc;
    private Activity atyToAttach;
    private View selCity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        atyToAttach = activity;

        //避免每次重新replace这个fragment的资源的重新申请，都对nul进行判断
        if(fvc==null)
            fvc = new FightWithViewControler(atyToAttach);

        if (fightWithView==null)
            fightWithView =  fvc.getFightWithView();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(atyToAttach instanceof ActionBarActivity) {
            ActionBar actionBar = ((ActionBarActivity) atyToAttach).getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar);
            selCity = actionBar.getCustomView().findViewById(R.id.id_city_select);
            selCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ColorDrawable cd = new ColorDrawable(0x000000);
                    WindowManager.LayoutParams lp = atyToAttach.getWindow().getAttributes();
                    lp.alpha = 0.4f;
                    atyToAttach.getWindow().setAttributes(lp);
                    PopupWindow mPopuWindow = new CitySelectorPopUp(atyToAttach).getPopUpWindow();
                    mPopuWindow.showAsDropDown(selCity);
                }
             });


        }else{
            LogMsgUtil.e("Fragment's activity not actionBarActivity");
        }


        viewList = new ArrayList<View>();


      /*  viewList.add(fightWithView);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewAdapter = new ViewAdapter(viewList);

        viewPager.setAdapter(viewAdapter);

        viewPager.setOnPageChangeListener(new Listener_PageChange());*/


        return fightWithView;
    }

    @Override
    public void onStart() {
        super.onStart();
        fvc.getDataFromNet(true);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mLocationManager.removeUpdates(lisloc);
    }

    class Listener_Location implements TencentLocationListener {

        @Override
        public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
            Log.i("whats", "wrong");
            if (error == TencentLocation.ERROR_OK) {
                Toast.makeText(atyToAttach, "loc" + tencentLocation.getAddress(),Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStatusUpdate(String s, int i, String s1) {

        }
    }

    class Listener_PageChange implements OnPageChangeListener {

        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub


        }

        public void onPageScrolled(int curPosition, float progress, int deltaPx) {
            // TODO Auto-generated method stub

            int nextPos;
            if (deltaPx > 0) {
                nextPos = curPosition + 1;
            } else {
                nextPos = curPosition - 1;
            }



        }

        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    }


}
