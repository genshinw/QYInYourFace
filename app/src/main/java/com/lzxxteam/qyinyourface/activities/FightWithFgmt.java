package com.lzxxteam.qyinyourface.activities;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.presenters.TeamListViewControler;
import com.lzxxteam.qyinyourface.tools.LogUtil;
import com.lzxxteam.qyinyourface.ui.CitySelectorPopUp;
import com.lzxxteam.qyinyourface.presenters.FightWithViewControler;
import com.lzxxteam.qyinyourface.ui.IndicaterViewPagerFactory;
import com.tencent.map.geolocation.TencentLocationManager;

import java.util.ArrayList;

/**
 *
 */
public class FightWithFgmt extends BaseFgmt {



    private ViewGroup fightWithView;

    private TencentLocationManager mLocationManager;
    private FightWithViewControler fvc;
    private View selCity;
    private IndicaterViewPagerFactory viewPagerFactory;
    private View selArea;
    private TeamListViewControler fvc2;
    private ViewGroup teamListView;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //避免每次重新replace这个fragment的资源的重新申请，都对nul进行判断
        if(fvc==null)
            fvc = new FightWithViewControler(atyToAttach,1,0);

        if (fightWithView==null)
            fightWithView =  fvc.getFightWithView();

        if(fvc2==null)
            fvc2 = new TeamListViewControler(atyToAttach);

        if (teamListView==null)
            teamListView =  fvc2.getTeamLsitView();

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
            selArea = actionBar.getCustomView().findViewById(R.id.id_tv_sel_fight_area);
            final View selAreaIndicate = actionBar.getCustomView()
                    .findViewById(R.id.id_iv_sel_area_indicate);

            selArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupWindow popwin = new PopupWindow(atyToAttach);
                    popwin.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    popwin.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                    popwin.setOutsideTouchable(true);
                    popwin.setFocusable(true);

                    popwin.setContentView(
                            LayoutInflater.from(atyToAttach)
                                    .inflate(R.layout.popup_sel_fight_area, null)
                    );
                    popwin.update();
                    popwin.showAsDropDown(selArea);

                }
            });


        }else{
            LogUtil.e("Fragment's activity not actionBarActivity");
        }


        viewPagerFactory = new IndicaterViewPagerFactory(atyToAttach);
        ArrayList<View> listView = new ArrayList<View>();
        listView.add(fightWithView);
        listView.add(teamListView);


        viewPagerFactory.addViewPagerViews(listView,null);

        container = viewPagerFactory.getIndicaterViewPager(new String[]{"约战信息","球队列表"});
        viewPagerFactory.showGoToFightBtn();


        return container;
    }

    @Override
    public void onStart() {
        super.onStart();
        fvc.getDataFromNet(true);
        fvc2.getDataFromNet(true);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }





}
