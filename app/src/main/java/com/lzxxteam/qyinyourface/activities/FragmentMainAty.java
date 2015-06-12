package com.lzxxteam.qyinyourface.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;

/**
 * 含有主界面的4个Aty
 * 父类继承ActionBarActivity（其继承FragmentActivity）
 * Created by Elvis on 2015/5/31.
 */
public  class FragmentMainAty extends BaseAty{

    protected ImageView menu0;
    protected ImageView menu1;
    protected ImageView menu2;
    protected ImageView menu3;
    protected Drawable menu0_press,menu1_press,menu2_press,menu3_press;
    private FragmentManager fragmentManager;
    private FightWithFgmt fightWithFgmt;
    private UserCentreFgmt userCentreFgmt;
    private DiscoverFgmt discoverFgmt;
    private ChatWithFgmt chatWithFgmt;

    private long mExitTime = 0;//控制双击退出程序

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.aty_base_main);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        fragmentManager = getSupportFragmentManager();
        initMenuButton();
        menu0.callOnClick();
    }


    private void initMenuButton() {
        View.OnClickListener changeMenu = new View.OnClickListener(){

            //用于控制ui切换来回颜色
            ImageView lastMenuItem;
            Drawable lastMenuDrawable;


            @Override
            public void onClick(View view) {

                //若本次和上一次一样则不切换
                if(((ImageView)view) == lastMenuItem)
                    return;

                //将上一次的imageView还原成白色
                if (lastMenuItem !=null)
                    lastMenuItem.setImageDrawable(lastMenuDrawable);

                //将本次的的drawable和imageview存为下一次用
                lastMenuItem = (ImageView) view;
                lastMenuDrawable = lastMenuItem.getDrawable();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                int viewId = view.getId();

                //切换到新的按钮
                switch (viewId) {
                    case R.id.menu_0:

                        menu0.setImageDrawable(menu0_press);
                        if(fightWithFgmt ==null){
                            fightWithFgmt = new FightWithFgmt();
                        }
                        transaction.replace(R.id.id_fl_main_view_container, fightWithFgmt);
                        break;

                    case R.id.menu_1:

                        menu1.setImageDrawable(menu1_press);
                        if(discoverFgmt ==null){
                            discoverFgmt = new DiscoverFgmt();
                        }
                        transaction.replace(R.id.id_fl_main_view_container, discoverFgmt);
                        break;
                    case R.id.menu_2:

                        menu2.setImageDrawable(menu2_press);
                        if(chatWithFgmt ==null){
                            chatWithFgmt = new ChatWithFgmt();
                        }
                        transaction.replace(R.id.id_fl_main_view_container, chatWithFgmt);
                        break;
                    case R.id.menu_3:


                        menu3.setImageDrawable(menu3_press);

                        if(userCentreFgmt ==null){
                            userCentreFgmt = new UserCentreFgmt();
                        }
                        transaction.replace(R.id.id_fl_main_view_container, userCentreFgmt);
                        break;
                }

                transaction.commit();
            }
        };
        menu0 = (ImageView) findViewById(R.id.menu_0);
        menu0.setOnClickListener(changeMenu);

        menu1 = (ImageView) findViewById(R.id.menu_1);
        menu1.setOnClickListener(changeMenu);

        menu2 = (ImageView) findViewById(R.id.menu_2);
        menu2.setOnClickListener(changeMenu);

        menu3 = (ImageView) findViewById(R.id.menu_3);
        menu3.setOnClickListener(changeMenu);


        menu0_press = AppGlobalMgr.getResImg(R.drawable.ic_menu0_press);
        menu1_press = AppGlobalMgr.getResImg(R.drawable.ic_menu1_press);
        menu2_press = AppGlobalMgr.getResImg(R.drawable.ic_menu2_press);
        menu3_press = AppGlobalMgr.getResImg(R.drawable.ic_menu3_press);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if ((System.currentTimeMillis() - mExitTime) > 2000) {

            Toast.makeText(FragmentMainAty.this,"再按一次退出程序", Toast.LENGTH_SHORT)
                    .show();
            mExitTime = System.currentTimeMillis();

        }
        //在该界面2秒内点击两次返回则退出app
        else {

            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return true;
    }
}
