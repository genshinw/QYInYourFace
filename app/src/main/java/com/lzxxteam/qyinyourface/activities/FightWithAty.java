package com.lzxxteam.qyinyourface.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.ui.CitySelectorPopUp;
import com.lzxxteam.qyinyourface.ui.FightWithViewControler;
import com.lzxxteam.qyinyourface.ui.ViewAdapter;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FightWithAty extends BaseMainAty {


    List<View> viewList;

    private View fightWithView;


    private ViewPager viewPager;

    private ViewAdapter viewAdapter;
    private TencentLocationManager mLocationManager;
    private Listener_Location lisloc;
    private FightWithViewControler fvc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//		显示自定义的ActionBar view
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar);

        menu0.setImageResource(R.drawable.m1_press);
        final View selCity = actionBar.getCustomView().findViewById(R.id.id_city_select);
        selCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable cd = new ColorDrawable(0x000000);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);
                PopupWindow mPopuWindow = new CitySelectorPopUp(FightWithAty.this).getPopUpWindow();
//				mPopuWindow.setBackgroundDrawable(cd); // 打开窗口时设置窗体背景透明度
                mPopuWindow.showAsDropDown(selCity);
            }
        });
        viewList = new ArrayList<View>();
        fvc = new FightWithViewControler(this);
        fightWithView = fvc.getFightWithView();

        addToMainContainer(fightWithView);
        fvc.getDataFromNet(true);
      /*  viewList.add(fightWithView);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewAdapter = new ViewAdapter(viewList);

        viewPager.setAdapter(viewAdapter);

        viewPager.setOnPageChangeListener(new Listener_PageChange());*/


        lisloc = new Listener_Location();
        mLocationManager = TencentLocationManager.getInstance(this);
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(5000);
        mLocationManager.requestLocationUpdates(request, lisloc);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(lisloc);
    }

    class Listener_Location implements TencentLocationListener {

        @Override
        public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
            Log.i("whats", "wrong");
            if (error == TencentLocation.ERROR_OK) {
                Toast.makeText(FightWithAty.this, "loc" + tencentLocation.getAddress(),Toast.LENGTH_LONG).show();
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
