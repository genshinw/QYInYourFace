package com.lzxxteam.qyinyourface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.ui.UserCentreViewControl;

/**
 * 含有主界面的4个Aty
 * Created by Elvis on 2015/5/31.
 */
public abstract  class BaseMainAty extends BaseAty{

    protected ImageView menu0, menu1, menu2, menu3;
    protected ViewGroup mainContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mainContainer = (ViewGroup) findViewById(R.id.id_ll_main_view_container);
        initMenuButton();
    }


    private void initMenuButton() {
        View.OnClickListener changeMenu = new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int viewId = view.getId();
                Intent intent;
                switch (viewId) {
                    case R.id.menu_0:
                        intent = new Intent(BaseMainAty.this, FightWithAty.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_3:
                         intent = new Intent(BaseMainAty.this, UserCentreAty.class);
                        startActivity(intent);
                        break;
                }
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
    }


    public void addToMainContainer(View v){
        mainContainer.addView(v);
    }
}
