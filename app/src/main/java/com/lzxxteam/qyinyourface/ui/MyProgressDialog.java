package com.lzxxteam.qyinyourface.ui;

import android.content.Context;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.activities.RegisterAty;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Elvis on 2015/6/6.
 */
public class MyProgressDialog {


    private final SweetAlertDialog pDialog;

    public MyProgressDialog(Context context,String title){
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText(title);
        pDialog.getProgressHelper().setBarWidth(25);
        pDialog.getProgressHelper()
                .setBarColor(AppGlobalMgr.getAppResources().getColor(R.color.myred));
    }

    public SweetAlertDialog getpDialog() {
        return pDialog;
    }

    public void show(){
        pDialog.show();
    }

    public void dismiss() {
        pDialog.dismiss();
    }
}
