package com.lzxxteam.qyinyourface.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;
import com.lzxxteam.qyinyourface.R;
/**
 * Created by Elvis on 15/5/23.
 */
public class CitySelectorPopUp {

    private  Context context;
    private PopupWindow popwin;

    public CitySelectorPopUp(Context context) {

        this.context = context;

    }

    public PopupWindow getPopUpWindow(){



        popwin = new PopupWindow(context);
        popwin.setWidth(TableLayout.LayoutParams.MATCH_PARENT);
        popwin.setHeight(TableLayout.LayoutParams.WRAP_CONTENT);
        popwin.setOutsideTouchable(true);
        popwin.setFocusable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_table, null);

        popwin.setContentView(
                LayoutInflater.from(context).inflate(R.layout.popup_table, null)
        );
        popwin.update();

        popwin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = ((Activity) context).getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1F;
                window.setAttributes(lp);
            }
        });
        return popwin;
        
    }



}
