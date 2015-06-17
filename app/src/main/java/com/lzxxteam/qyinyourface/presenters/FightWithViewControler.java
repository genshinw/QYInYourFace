package com.lzxxteam.qyinyourface.presenters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.activities.FightWithDetailAty;
import com.lzxxteam.qyinyourface.model.FightWithData;
import com.lzxxteam.qyinyourface.model.NetPackData;
import com.lzxxteam.qyinyourface.net.GetHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppConstantValue;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.tools.HiddenToolBarCtrl;
import com.lzxxteam.qyinyourface.tools.LogMsgUtil;
import com.lzxxteam.qyinyourface.ui.FightWithAdapter;
import com.lzxxteam.qyinyourface.ui.RefreshLayout;

import org.apache.http.Header;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithViewControler {


    private  ArrayList<FightWithData> datas;
    private FightWithAdapter adapter;
    private Context context;
    private ListView fightWithView;
    private RefreshLayout refreshLayout;
    private ViewGroup basePraent;
    private View filterView;
    private View header;
    private HiddenToolBarCtrl hiddenToolBar;

    public FightWithViewControler(Context context){

        this.context = context;
        datas = new ArrayList<FightWithData>();
        //滚动过多少距离后才开始计算是否隐藏/显示头尾元素。这里用了默认touchslop的0.9倍。
    }

    public ViewGroup getFightWithView() {
        basePraent = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fgmt_fight_with,null);

        filterView = basePraent.findViewById(R.id.id_ll_filter);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        filterView.measure(w, h);
        int height = filterView.getMeasuredHeight();



        adapter = new FightWithAdapter(context,datas);
        if(fightWithView==null) {
            fightWithView = new ListView(context);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.MATCH_PARENT
            );
            fightWithView.setLayoutParams(layoutParams);

        }
        fightWithView.setAdapter(adapter);


        header = new View(context);
        header.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height));
        header.setBackgroundColor(Color.parseColor("#00000000"));
        fightWithView.addHeaderView(header);
        fightWithView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent((Activity) context, FightWithDetailAty.class);
                ((Activity) context).startActivity(intent);
            }
        });
        refreshLayout = (RefreshLayout) basePraent.findViewById(R.id.id_refl_fight_with);
        /*refreshLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));*/
        refreshLayout.addView(fightWithView);
        refreshLayout.setFooterView(context, fightWithView, R.layout.lv_footer_fresh);
        refreshLayout.setProgressViewOffset(true, height / 2, height + height / 5);
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
        hiddenToolBar = new HiddenToolBarCtrl(context,filterView, fightWithView);
        fightWithView.setOnTouchListener(hiddenToolBar.onTouchListener);
        fightWithView.setOnScrollListener(hiddenToolBar.onScrollListener);
//        basePraent.addView(refreshLayout);
        return basePraent;
    }

    public void getDataFromNet(final boolean isRefresh) {
        new GetHttpCilent(context).execRequest(AppConstantValue.URL_TEST_DIR+"fightlist.json", new BaseJsonHttpResponseHandler<ArrayList<FightWithData>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ArrayList<FightWithData> response) {

                if (refreshLayout.isRefreshing())
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
                if (isRefresh)
                    datas.clear();
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = jp.getCurrentName();
                    // Let's move to value
                    jp.nextToken();
                    if (fieldName.equals("head")) {
                        NetPackData netPackData = mapper.readValue(jp, NetPackData.class);
                    } else if (fieldName.equals("data")) {
                        while (jp.nextToken() == JsonToken.START_OBJECT) {
                            /*TreeNode nodes = mapper.readTree(jp);
                            nodes.get("packHead");*/
                            datas.add(mapper.readValue(jp, FightWithData.class));
                        }
                    } else { // ignore, or signal error?
                        throw new IOException("Unrecognized field '"+fieldName+"'");
                    }
                }
                jp.close(); // im

                return datas;
            }
        });
    }

    public void refreshView(){

        adapter.notifyDataSetChanged();
    }



}
