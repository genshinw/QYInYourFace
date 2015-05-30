package com.lzxxteam.qyinyourface.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
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
public class MainActivity extends ActionBarActivity {


    List<View> viewList;

    private View fightWithView, view2, view3, userCentreView;
    private ImageView menu0, menu1, menu2, menu3, menu0_press, menu1_press, menu2_press, menu3_press;

    private ViewPager viewPager;

    private ViewAdapter viewAdapter;
    private ActionBar actionBar;
    private TencentLocationManager mLocationManager;
    private Listener_Location lisloc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.CANDY);
        actionBar = getSupportActionBar();

//		隐藏logo
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
//		显示自定义的ActionBar view
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar);
        final View selCity = actionBar.getCustomView().findViewById(R.id.id_city_select);
        selCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable cd = new ColorDrawable(0x000000);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);
                PopupWindow mPopuWindow = new CitySelectorPopUp(MainActivity.this).getPopUpWindow();
//				mPopuWindow.setBackgroundDrawable(cd); // 打开窗口时设置窗体背景透明度
                mPopuWindow.showAsDropDown(selCity);
            }
        });
        viewList = new ArrayList<View>();
        fightWithView = new FightWithViewControler(this, null).getFightWithView();
        view2 = LayoutInflater.from(this).inflate(R.layout.pageview2, null);
        view3 = LayoutInflater.from(this).inflate(R.layout.pageview3, null);
        userCentreView = LayoutInflater.from(this).inflate(R.layout.pageview_user_centre, null);
        viewList.add(fightWithView);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(userCentreView);
        initMenuButton();


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewAdapter = new ViewAdapter(viewList);

        viewPager.setAdapter(viewAdapter);

        viewPager.setOnPageChangeListener(new Listener_PageChange());


        lisloc = new Listener_Location();
        mLocationManager = TencentLocationManager.getInstance(this);
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(5000);
        mLocationManager.requestLocationUpdates(request, lisloc);
    }

    private void initMenuButton() {
        menu0 = (ImageView) findViewById(R.id.menu_0);
        menu0_press = (ImageView) findViewById(R.id.menu_press_0);
        menu1 = (ImageView) findViewById(R.id.menu_1);
        menu1_press = (ImageView) findViewById(R.id.menu_press_1);
        menu2 = (ImageView) findViewById(R.id.menu_2);
        menu2_press = (ImageView) findViewById(R.id.menu_press_2);
        menu3 = (ImageView) findViewById(R.id.menu_3);
        menu3_press = (ImageView) findViewById(R.id.menu_press_3);
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
                Toast.makeText(MainActivity.this, "loc" + tencentLocation.getAddress(),Toast.LENGTH_LONG).show();
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


            if (nextPos >= 0 && nextPos < 4) {

                View curPage = viewList.get(curPosition);
                View nextPage = viewList.get(nextPos);

                curPage.setAlpha(1 - progress);
                nextPage.setAlpha(progress);

					/*curPage.setScaleX(1-(progress/2));
                    curPage.setScaleY(1-(progress/2));

					nextPage.setScaleY(0.5F+progress/2);
					nextPage.setScaleX(0.5F+progress/2);*/

            }

            switch (nextPos) {

                case 0:
                    menu0_press.setAlpha(progress);
                    menu1_press.setAlpha(1 - progress);
                    menu0.setAlpha(1 - progress);
                    menu1.setAlpha(progress);
                    break;
                case 1:
                    if (curPosition == 0) {
                        menu1_press.setAlpha(progress);
                        menu0_press.setAlpha(1 - progress);
                        menu1.setAlpha(1 - progress);
                        menu0.setAlpha(progress);
                        break;
                    }
                    menu1_press.setAlpha(progress);
                    menu2_press.setAlpha(1 - progress);
                    menu1.setAlpha(1 - progress);
                    menu2.setAlpha(progress);
                    break;
                case 2:
                    if (curPosition == 1) {
                        menu2_press.setAlpha(progress);
                        menu1_press.setAlpha(1 - progress);
                        menu2.setAlpha(1 - progress);
                        menu1.setAlpha(progress);
                        break;
                    }
                    menu2_press.setAlpha(progress);
                    menu3_press.setAlpha(1 - progress);
                    menu2.setAlpha(1 - progress);
                    menu3.setAlpha(progress);
                    break;
                case 3:
                    menu3_press.setAlpha(progress);
                    menu2_press.setAlpha(1 - progress);
                    menu3.setAlpha(1 - progress);
                    menu2.setAlpha(progress);
                    break;


            }

        }

        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    }


}
