package com.lzxxteam.qyinyourface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.ui.IndicaterViewPagerFactory;
import com.tencent.tencentmap.mapsdk.map.MapView;

import java.util.ArrayList;

/**
 * Created by Elvis on 2015/6/11.
 */
public class ChatWithFgmt extends BaseFgmt {


    private ViewGroup viewPager;
    private IndicaterViewPagerFactory viewPagerFactory;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setActionBarTitle(AppGlobalMgr.getResString(R.string.fgmt_name_chat_with));

        viewPagerFactory = new IndicaterViewPagerFactory(atyToAttach);
        View view1 = inflater.inflate(R.layout.pageview3,null);
        View view2 = inflater.inflate(R.layout.pageview3,null);
        ArrayList<View> listView = new ArrayList<View>();
        listView.add(view1);
        listView.add(view2);
        viewPagerFactory.addViewPagerViews(listView, null);

        container = viewPagerFactory.getIndicaterViewPager(new String[]{"私信","通讯录"});


        return container;
    }



}
