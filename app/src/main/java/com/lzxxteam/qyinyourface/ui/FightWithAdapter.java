package com.lzxxteam.qyinyourface.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzxxteam.qyinyourface.R;
import java.util.List;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithAdapter extends BaseAdapter {

    private  List<FightWithData> dataList;
    private  Context context;

    public FightWithAdapter(Context context,List<FightWithData> dataList){

        this.context = context;
        this.dataList = dataList;
    }

    @Override

    public int getCount() {

        int count = 0;
        if(dataList!=null)
            count = dataList.size();

        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        FightWithData data = dataList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_fightwith_item, null);
            holder =  new ViewHolder(convertView);
            convertView.setTag(holder);

        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setHolder(data);
        return convertView;
    }
    private  class ViewHolder{
        private View convertView;

        public ViewHolder(View convertView){

            this.convertView = convertView;

        }

        public void setHolder(FightWithData data){
            ImageView portrait = (ImageView) convertView.findViewById(R.id.plist_logo);
            TextView userName = (TextView) convertView.findViewById(R.id.plist_user_name);
            TextView fightTime = (TextView) convertView.findViewById(R.id.plist_game_time);
            TextView fightSpace = (TextView) convertView.findViewById(R.id.plist_game_space);


            portrait.setImageDrawable(data.getUserData().getUserPortrait());
            userName.setText(data.getUserData().getUserName());
            fightSpace.setText(data.getSpace());
            fightTime.setText(data.getFightTime());
         }

    }
}
