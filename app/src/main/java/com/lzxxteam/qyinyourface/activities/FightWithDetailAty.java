package com.lzxxteam.qyinyourface.activities;

import android.os.Bundle;
import android.view.View;

import com.lzxxteam.qyinyourface.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Elvis on 2015/6/6.
 */
public class FightWithDetailAty extends BaseAty{

    private View btn_access_fight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aty_fight_with_detail);
        
        btn_access_fight = findViewById(R.id.id_aty_fight_detail_access);
        btn_access_fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(FightWithDetailAty.this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("是否和User1球队约战")
                        .setConfirmText("约起")
                        .setCancelText("不约")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.setTitleText("请求已发送")
                                        .setConfirmText("确定")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        })
                                        .setCancelText(null)
                                        .setCancelable(false);

                                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        }).show();
            }
        });
    }
}
