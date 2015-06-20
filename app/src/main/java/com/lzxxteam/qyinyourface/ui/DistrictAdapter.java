package com.lzxxteam.qyinyourface.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.LogUtil;

import java.util.ArrayList;

/**
 * Created by Elvis on 2015/6/18.
 */
public class DistrictAdapter extends BaseAdapter{

    private final Context context;
    private ArrayList<String> districtStrings;
    private int  screentSum;
    private int screenNo = -1;

    public DistrictAdapter(Context context,ArrayList<String> districtStrings) {
        this.context =context;
        setDistrictStrings(districtStrings);

    }

    public void setDistrictStrings(ArrayList<String> districtStrings) {
        this.districtStrings = districtStrings;
        if( districtStrings.size() % 3==0) {
            screentSum =districtStrings.size() /3;
        }
        else {
            screentSum =districtStrings.size() / 3+1;
        }
        LogUtil.d("screenSum"+screentSum);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        if(districtStrings.size()==0){

            return 0;
        }

        if(screenNo==screentSum-1 && districtStrings.size() % 3!=0){
            return districtStrings.size() % 3;
        }
        return 3;
    }

    @Override
    public String getItem(int position) {
        /*String district = districtStrings.get(screenNo*3+position);
        LogUtil.d("disrict:"+screenNo*3+position+district);

        return district;*/
        return "";
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

        ((TextView) convertView).setText(districtStrings.get(screenNo*3+position));
        return convertView;
    }


    public boolean next(){

        if (screenNo<screentSum-1) {
            screenNo++;
        }else  {
            screenNo = 0;
        }
        LogUtil.d("nowScreenNO"+screenNo);
        return true;
    }
}
