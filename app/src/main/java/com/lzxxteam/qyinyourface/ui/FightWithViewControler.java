package com.lzxxteam.qyinyourface.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.lzxxteam.qyinyourface.model.FightWithData;
import com.lzxxteam.qyinyourface.net.GetHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithViewControler {


    private  ArrayList<FightWithData> datas;
    private  FightWithAdapter adapter;
    private Context context;
    private ListView fightWithView;
    private RefreshLayout refreshLayout;

    public FightWithViewControler(Context context){

        this.context = context;
        datas = new ArrayList<FightWithData>();
        adapter = new FightWithAdapter(context,datas);


    }

    public ViewGroup getFightWithView() {

        if(fightWithView==null) {
            fightWithView = new ListView(context);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.MATCH_PARENT
            );
            fightWithView.setLayoutParams(layoutParams);

        }
        fightWithView.setAdapter(adapter);
        fightWithView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  Intent intent = new Intent((Activity)context,FightWithDetailAty.class);
                ((Activity)context).startActivity(intent);
            }
        });

        refreshLayout = new RefreshLayout(context);
        refreshLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        refreshLayout.addView(fightWithView);
        refreshLayout.setFooterView(context, fightWithView, R.layout.lv_footer_fresh);


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
        new GetHttpCilent(context).execRequest("testFightWith.json", new BaseJsonHttpResponseHandler<ArrayList<FightWithData>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse,ArrayList<FightWithData> response) {

                if(refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);
                refreshLayout.setLoading(false);
                refreshView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ArrayList<FightWithData> errorResponse) {

            }

            @Override
            protected ArrayList<FightWithData> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                ObjectMapper mapper = new ObjectMapper();
                JsonParser jp = new JsonFactory().createParser(rawJsonData);
                //跳过JsonToken.START_ARRAY
                jp.nextToken();

                //刷新则要将数据清空
                if(isRefresh)
                    datas.clear();

                while (jp.nextToken()== JsonToken.START_OBJECT){

                    datas.add(mapper.readValue(jp,FightWithData.class));
                }
                //做一个1s延迟
                TimeUnit.SECONDS.sleep(1);
                return  datas;
            }
        });
    }

    public void refreshView(){

        adapter.notifyDataSetChanged();
    }


}
