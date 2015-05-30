package com.lzxxteam.qyinyourface.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.FightWithData;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithAdapter extends BaseAdapter {

    //头像载入地址
    private static  final String URL_PORTTRAIT = "http://172.30.66.158/profile/";
    private final ImageLoader imageLoader;

    private  List<FightWithData> dataList;
    private  Context context;

    public FightWithAdapter(Context context,List<FightWithData> dataList){

        this.context = context;
        this.dataList = dataList;

        /**
         * 对imageLoader加载网络头像
         */
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
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
            holder =  new ViewHolder(convertView,position);
            convertView.setTag(holder);

        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setHolder(data);
        return convertView;
    }
    private  class ViewHolder{
        private View convertView;
        private  int pos;
        public ViewHolder(View convertView,int pos){

            this.convertView = convertView;
            this.pos = pos;

        }

        public void setHolder(FightWithData data){
            ImageView portrait = (ImageView) convertView.findViewById(R.id.plist_logo);
            TextView userName = (TextView) convertView.findViewById(R.id.plist_user_name);
            TextView fightTime = (TextView) convertView.findViewById(R.id.plist_game_time);
            TextView fightSpace = (TextView) convertView.findViewById(R.id.plist_game_space);
            imageLoader.displayImage(URL_PORTTRAIT + data.getUserName()+".png",portrait);

            userName.setText(data.getUserName());
            fightSpace.setText(data.getFightSpace());
            fightTime.setText(data.getFightTime());
         }

    }
}
