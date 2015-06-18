package com.lzxxteam.qyinyourface.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzxxteam.qyinyourface.R;

/**
 * Created by Elvis on 2015/6/18.
 */
public class DistrictAdapter extends BaseAdapter{

    private final Context context;
    private  String[] districtStrings;
    private int  screentSum;
    private int screenNo = 0;

    public DistrictAdapter(Context context, String[] objects) {
        this.context =context;
        this.districtStrings = objects;
        if( objects.length % 3==0) {
            screentSum =objects.length /3;
        }
        else {
            screentSum =objects.length / 3+1;
        }

    }

    @Override
    public int getCount() {
        if(screenNo==screentSum-1 && districtStrings.length % 3!=0){
            return districtStrings.length % 3;
        }
        return 3;
    }

    @Override
    public String getItem(int position) {
        return districtStrings[screenNo*3+position];
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.gv_filter_item,null);
        }
        ((TextView)convertView).setText(getItem(position));
        return convertView;
    }


    public boolean next(){
        if (screenNo<screentSum-1) {
            screenNo++;
            return true;
        }else if(screenNo>0) {
            screenNo--;
            return true;
        }
        return   false;
    }
}
