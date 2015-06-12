package com.lzxxteam.qyinyourface.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Elvis on 2015/6/11.
 */
public class IndicaterViewPagerFactory {

    private Context context;
    private int splitWith;
    private List<View> viewPagerViewList = new ArrayList<View>();
    private HashMap<View, PageSelectPresetnters> pagePresenterMap
            = new HashMap<View, PageSelectPresetnters>();

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdpater;

    private TextView indicaterText2;
    private TextView indicaterText1;
    private ImageView indicaterLine;

    private ViewGroup container;
    private String[] indicateNames;


    public IndicaterViewPagerFactory(Context context) {
        this.context = context;
    }

    public ViewGroup getIndicaterViewPager(String[] indicateNames) {
        this.indicateNames = indicateNames;
        if (container==null)
            init();

        return container;
    }
    public void init() {

        container = (ViewGroup) LayoutInflater.from(context)
                .inflate(R.layout.viewpager_indicater, null);

     /*   View floatButton =  LayoutInflater.from(context)
                .inflate(R.layout.fgmt_fight_with, null);

        container.addView(floatButton);*/
        indicaterLine = (ImageView) container.findViewById(R.id.id_iv_move_line);
        indicaterText1 = (TextView)container.findViewById(R.id.id_tv_indicater_gyms);
        indicaterText1.setText(indicateNames[0]);
        indicaterText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.arrowScroll(View.FOCUS_BACKWARD);
            }
        });


        indicaterText2 = (TextView)container.findViewById(R.id.id_tv_indicater_teams);
        indicaterText2.setText(indicateNames[1]);
        indicaterText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.arrowScroll(View.FOCUS_FORWARD);
            }
        });

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) indicaterLine.getLayoutParams();
        splitWith = AppGlobalMgr.getScreenWidth()/2;
        lp.width = splitWith;
        indicaterLine.setLayoutParams(lp);

        viewPager =  (ViewPager)container.findViewById(R.id.id_vp_view);

        viewPagerAdpater = new ViewPagerAdapter(viewPagerViewList);
        viewPager.setAdapter(viewPagerAdpater);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentColor = AppGlobalMgr.getResColor(R.color.myred);
            int nocurrentColor = AppGlobalMgr.getResColor(R.color.mygreylighter);

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (positionOffset == 0)
                    return;

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) indicaterLine.getLayoutParams();
                if (positionOffsetPixels > 0) {
                    lp.leftMargin = (int) (positionOffset * splitWith);
                } else if ((positionOffsetPixels < 0)) {
                    lp.leftMargin = (int) (1 - positionOffset) * splitWith;
                }
                indicaterLine.setLayoutParams(lp);

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    indicaterText1.setTextColor(currentColor);
                    indicaterText2.setTextColor(nocurrentColor);
                    PageSelectPresetnters pasep = pagePresenterMap.get(viewPagerViewList.get(0));
                    if ((pasep) != null) {
                        pasep.pageDeal();
                    }

                } else if (position == 1) {
                    indicaterText2.setTextColor(currentColor);
                    indicaterText1.setTextColor(nocurrentColor);
                    PageSelectPresetnters pasep = pagePresenterMap.get(viewPagerViewList.get(1));
                    if ((pasep) != null) {
                        pasep.pageDeal();
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public  void addViewPagerViews(
            List<View> viewPagerViewList
            ,HashMap<View,PageSelectPresetnters> pagePresenterMap){

        if(viewPagerViewList!=null) {
            this.viewPagerViewList = viewPagerViewList;
        }


        if (pagePresenterMap!=null) {
            this.pagePresenterMap = pagePresenterMap;
        }


    }

    interface PageSelectPresetnters {
        void pageDeal();
    }
}
