package com.lzxxteam.qyinyourface.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.TeamBaseData;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.tools.GetImageFromNet;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Elvis on 15/5/22.
 */
public class TeamListAdapter extends BaseAdapter {

    //头像载入地址
    private final ImageLoader imageLoader;
    private int[] levelDraws = {R.drawable.level0,R.drawable.level1,
            R.drawable.level2,R.drawable.level3,
    R.drawable.level4,R.drawable.level5};

    private  List<TeamBaseData> dataList;
    private  Context context;
    private  int dataSize = 0;
    public TeamListAdapter(Context context, List<TeamBaseData> dataList){

        this.context = context;
        this.dataList = dataList;
        /**
         * 对imageLoader加载网络头像
         */
        imageLoader = ImageLoader.getInstance();
    }

    @Override

    public int getCount() {
        return dataSize;
    }

    @Override
    public void notifyDataSetChanged() {
        dataSize = dataList.size();
        super.notifyDataSetChanged();
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
        TeamBaseData data = dataList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_teams_item, null);
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

        public void setHolder(TeamBaseData data){

            ImageView teamPortrait = (ImageView) convertView
                    .findViewById(R.id.id_iv_team_list_logo);
            TextView teamName = (TextView) convertView.findViewById(R.id.id_tv_team_list_name);
            TextView winTimes = (TextView) convertView.findViewById(R.id.id_tv_team_list_wintimes);
            ImageView teamLevel = (ImageView) convertView.findViewById(R.id.id_iv_team_list_level);
            GetImageFromNet.setProfileToImageView((data.getId()%15+1) + "t.png", teamPortrait);
            teamLevel.setImageDrawable(AppGlobalMgr.getResImg(levelDraws[data.getTeamLevel()]));
            teamName.setText(data.getTeamName());
            winTimes.setText("获胜率：" + data.getTeamWinLevel() + "%");
         }

    }
}
