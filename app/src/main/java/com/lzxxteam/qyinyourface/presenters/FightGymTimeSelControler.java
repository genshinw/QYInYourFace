package com.lzxxteam.qyinyourface.presenters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.lzxxteam.qyinyourface.R;

import java.util.zip.Inflater;

/**
 * Created by Elvis on 2015/6/17.
 */
public class FightGymTimeSelControler {

    private Context context;

    public FightGymTimeSelControler(Context context) {
        this.context = context;
    }

    public void showGymTimeDialog(){

        ViewGroup dialogView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.lv_gyms_time_item,null);
        Spinner gymTimeSpin = (Spinner) dialogView.findViewById(R.id.id_spin_gym_time_sel_num);
        Spinner gymTimeSpin2 = (Spinner) dialogView.findViewById(R.id.id_spin_gym_time_sel_num2);
        Spinner gymTimeSpin3 = (Spinner) dialogView.findViewById(R.id.id_spin_gym_time_sel_num3);
        String[] gymNums = {"一号场","二号场","三号场"};
        ArrayAdapter<String> gymNumsAdapter = new ArrayAdapter<String>
                (context,R.layout.time_sel_spinner,gymNums);


        gymTimeSpin.setAdapter(gymNumsAdapter);
        gymTimeSpin.setPrompt(gymNums[0]);
        gymTimeSpin2.setAdapter(gymNumsAdapter);
        gymTimeSpin2.setPrompt(gymNums[1]);
        gymTimeSpin3.setAdapter(gymNumsAdapter);
        gymTimeSpin3.setPrompt(gymNums[0]);
        new AlertDialog.Builder(context).setView(dialogView).create().show();

    }
}
