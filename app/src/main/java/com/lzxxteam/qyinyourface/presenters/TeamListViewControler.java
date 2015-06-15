package com.lzxxteam.qyinyourface.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.activities.FightWithDetailAty;
import com.lzxxteam.qyinyourface.model.TeamBaseData;
import com.lzxxteam.qyinyourface.net.GetHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppConstantValue;
import com.lzxxteam.qyinyourface.ui.FightWithAdapter;
import com.lzxxteam.qyinyourface.ui.RefreshLayout;
import com.lzxxteam.qyinyourface.ui.TeamListAdapter;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Elvis on 15/5/22.
 */
public class TeamListViewControler {


    private  ArrayList<TeamBaseData> datas;
    private TeamListAdapter adapter;
    private Context context;
    private ListView teamListView;
    private RefreshLayout refreshLayout;

    public TeamListViewControler(Context context){

        this.context = context;
        datas = new ArrayList<TeamBaseData>();
       adapter = new TeamListAdapter(context,datas);


    }

    public ViewGroup getTeamLsitView() {

        if(teamListView ==null) {
            teamListView = new ListView(context);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.MATCH_PARENT
            );
            teamListView.setLayoutParams(layoutParams);

        }
        teamListView.setAdapter(adapter);
        teamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent((Activity) context, FightWithDetailAty.class);
                ((Activity) context).startActivity(intent);
            }
        });

        refreshLayout = new RefreshLayout(context);
        refreshLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        refreshLayout.addView(teamListView);
        refreshLayout.setFooterView(context, teamListView, R.layout.lv_footer_fresh);


        refreshLayout.setColorSchemeResources(R.color.myred);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet(true);
            }
        });
        refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                getDataFromNet(false);

            }
        });

        return refreshLayout;
    }

    public void getDataFromNet(final boolean isRefresh) {
        new GetHttpCilent(context).execRequest(AppConstantValue.URL_TEST_DIR+"testTeamList.json", new BaseJsonHttpResponseHandler<ArrayList<TeamBaseData>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse,ArrayList<TeamBaseData> response) {

                if(refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);
                refreshLayout.setLoading(false);
                refreshView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<TeamBaseData> errorResponse) {

            }

            @Override
            protected ArrayList<TeamBaseData> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                ObjectMapper mapper = new ObjectMapper();
                JsonParser jp = new JsonFactory().createParser(rawJsonData);
                //跳过JsonToken.START_ARRAY
                jp.nextToken();

                //刷新则要将数据清空
                if(isRefresh)
                    datas.clear();

                while (jp.nextToken()== JsonToken.START_OBJECT){

                    datas.add(mapper.readValue(jp,TeamBaseData.class));
                }
                return  datas;
            }
        });
    }

    public void refreshView(){

        adapter.notifyDataSetChanged();
    }


}
