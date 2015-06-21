package com.lzxxteam.qyinyourface.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.presenters.TeamListViewControler;
import com.lzxxteam.qyinyourface.tools.LBSHelper;
import com.lzxxteam.qyinyourface.tools.LogUtil;
import com.lzxxteam.qyinyourface.ui.CitySelectorPopUp;
import com.lzxxteam.qyinyourface.presenters.FightWithViewControler;
import com.lzxxteam.qyinyourface.ui.IndicaterViewPagerFactory;
import com.tencent.map.geolocation.TencentLocationManager;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *
 */
public class FightWithFgmt extends BaseFgmt {


    private ViewGroup fightWithViewFormal;

    private TencentLocationManager mLocationManager;
    private FightWithViewControler fvcFormal;
    private View selCity;
    private IndicaterViewPagerFactory viewPagerFactory;
    private View selArea;
    private TeamListViewControler tvc;
    private ViewGroup teamListView;
    private ViewGroup fightListFormal;
    private ViewSwitcher viewswitch;
    private FightWithViewControler fvcFree;
    private ViewGroup fightWithViewFree;
    private ArrayList<View> viewPagerViewArray;
    private int freeOrFormal = 1;//一开始是自由区页面
    final int GUANGZHOU_ID = 440100;
    final int SHEN_ZHEN_ID = 440300;
    private AlertDialog mAlert;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //避免每次重新replace这个fragment的资源的重新申请，都对nul进行判断


        //正式区的应战列表和球队列表
        if (fvcFormal == null)
            fvcFormal = new FightWithViewControler(atyToAttach, 2, 0);



        if (tvc == null)
            tvc = new TeamListViewControler(atyToAttach);


        viewPagerFactory = new IndicaterViewPagerFactory(atyToAttach);

        //自由区的应战列表
        if (fvcFree == null)
            fvcFree = new FightWithViewControler(atyToAttach, 1, 0);


        viewPagerViewArray = new ArrayList<View>();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (atyToAttach instanceof ActionBarActivity) {
            final ActionBar actionBar = ((ActionBarActivity) atyToAttach).getSupportActionBar();
            actionBar.setCustomView(R.layout.actionbar);

            ViewGroup dialogView = (ViewGroup) LayoutInflater.from(atyToAttach)
                    .inflate(R.layout.aty_sel_provice, null);
            dialogView.findViewById(R.id.id_sel_shenzhen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fvcFree.changeProvice(SHEN_ZHEN_ID);
                    ((TextView)selCity).setText("深圳");
                    mAlert.dismiss();

                }
            });
            dialogView.findViewById(R.id.id_sel_guangzhou).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fvcFree.changeProvice(GUANGZHOU_ID);
                    ((TextView)selCity).setText("广州");
                    mAlert.dismiss();

                }
            });
            mAlert = new AlertDialog.Builder(atyToAttach).setView(dialogView)
                    .create();


            selCity = actionBar.getCustomView().findViewById(R.id.id_city_select);
            selCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*ColorDrawable cd = new ColorDrawable(0x000000);
                    WindowManager.LayoutParams lp = atyToAttach.getWindow().getAttributes();
                    lp.alpha = 0.4f;
                    atyToAttach.getWindow().setAttributes(lp);
                    PopupWindow mPopuWindow = new CitySelectorPopUp(atyToAttach).getPopUpWindow();
                    mPopuWindow.showAsDropDown(selCity);*/
                    mAlert.show();
                    //fvcFree.changeProvice(SHEN_ZHEN_ID);

                    //((TextView)selCity).setText("深圳");;
                }
            });
            selArea = actionBar.getCustomView().findViewById(R.id.id_ll_sel_fight_area);
            selArea.setOnClickListener(new View.OnClickListener() {
                ImageView selAreaIndicate = (ImageView) actionBar.getCustomView()
                        .findViewById(R.id.id_iv_sel_area_indicate);
                TextView selAreaName = (TextView) actionBar.getCustomView()
                        .findViewById(R.id.id_tv_sel_area_name);

                @Override
                public void onClick(View v) {
                    if (viewswitch != null) {

                        viewswitch.showNext();
                        switch (freeOrFormal) {
                            //若此时是自由区
                            case 1:
                                selAreaIndicate.setImageResource(R.drawable.ic_formal_area_anchor);
                                freeOrFormal = 2;
                                selAreaName.setText("正式区");
                                break;
                            //若此时是正式区
                            case 2:
                                selAreaIndicate.setImageResource(R.drawable.ic_free_area_anchor);
                                freeOrFormal = 1;
                                selAreaName.setText("自由区");


                                break;

                        }
                    }

                }
            });


        } else {
            LogUtil.e("Fragment's activity not actionBarActivity");
        }

            if (viewswitch == null) {
                viewswitch = new ViewSwitcher(atyToAttach);
                viewswitch.setFactory(new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {

                        if(freeOrFormal==1) {
                            if (fightWithViewFree == null) {
                                fightWithViewFree = fvcFree.getFightWithView();
                                fvcFree.changeProvice(SHEN_ZHEN_ID);

                            }

                            freeOrFormal = 2;
                            return fightWithViewFree;
                        }

                        if (fightListFormal == null) {

                            if (fightWithViewFormal == null) {
                                fightWithViewFormal = fvcFormal.getFightWithView();
                                viewPagerViewArray.add(fightWithViewFormal);

                            }
                            if (teamListView == null) {
                                teamListView = tvc.getTeamLsitView();
                                viewPagerViewArray.add(teamListView);
                            }

                            viewPagerFactory.addViewPagerViews(viewPagerViewArray, null);
                            fightListFormal = viewPagerFactory.getIndicaterViewPager(new String[]{"约战信息", "球队列表"});
                        }
                        freeOrFormal = 1;

                        return fightListFormal;
                    }
                });
//                viewswitch.addView(fightListFormal);
                viewswitch.setInAnimation(AnimationUtils.loadAnimation(atyToAttach,
                        android.R.anim.slide_in_left));
                viewswitch.setOutAnimation(AnimationUtils.loadAnimation(atyToAttach,
                        android.R.anim.slide_out_right));
            }

            container = viewswitch;

            return container;
        }


}
