package com.lzxxteam.qyinyourface.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.activities.FightWithDetailAty;
import com.lzxxteam.qyinyourface.model.FightWithData;
import com.lzxxteam.qyinyourface.model.NetPackData;
import com.lzxxteam.qyinyourface.net.PostHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppConstantValue;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.ui.DistrictAdapter;
import com.lzxxteam.qyinyourface.ui.HiddenToolBarCtrl;
import com.lzxxteam.qyinyourface.tools.LogUtil;
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
    private boolean isRefresh;
    private PostHttpCilent postHttpCilent;
    /**
     * 列表偏移，0——刷新列表
     */
    private Integer nowpos;

    /**
     * 约战列表类型，1——自由区，2——固定区
     */
    private int listType;

    //筛选条件，默认都是不限制

    /**
     * 地区码，	0——不限制
     */
    private int district = 0;
    /**
     * 时间范围，0——不限制，1——三天内，2——一周内，3——一个月内
     */
    private int timescope = 0;
    /**
     * 赛制规则，    0——不限制，1——5人球赛，2——3人球赛
     */
    private int rule = 0;
    private GridView gridView;
    private DistrictAdapter districtAdapter;
    private ViewSwitcher viewSwitcher;
    private GridView fliterTime;
    private View locNoFlit,timeNoFlit;


    /**
     *
     * @param context
     * @param listType 约战列表类型，1——自由区，2——固定区
     * @param district 地区码，	0——不限制
     */
    public FightWithViewControler(Context context,int listType,int district){

        this.context = context;
        datas = new ArrayList<FightWithData>();
        this.listType = listType;
        this.district = district;

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
        viewSwitcher = (ViewSwitcher) basePraent.findViewById(R.id.id_vs_filter_district);
        String[] areasStr = new String[]{"南山区","福田区","盐田区","罗湖区","宝安区","龙岗区"};
        districtAdapter = new DistrictAdapter(context, areasStr);


        viewSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                gridView = (GridView) LayoutInflater.from(context).inflate(R.layout.gv_fliter, null);
                gridView.setAdapter(districtAdapter);
                return gridView;
            }
        });
        viewSwitcher.setInAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.slide_in_left));
        viewSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.slide_out_right));


        basePraent.findViewById(R.id.id_iv_filter_district_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (districtAdapter.next())
                    viewSwitcher.showNext();
            }
        });

        locNoFlit = basePraent.findViewById(R.id.id_tv_fliter_loc_noflit);
        timeNoFlit = basePraent.findViewById(R.id.id_tv_fliter_time_noflit);

        fliterTime = (GridView) basePraent.findViewById(R.id.id_gv_filter_time);
        fliterTime.setAdapter(new ArrayAdapter<String>(context, R.layout.gv_filter_item
                , new String[]{"三天内", "一天内", "一周内"}));
        fliterTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View lastView = null;
            Drawable pinkRect = AppGlobalMgr.getResImg(R.drawable.bg_pink_rectangle);
            Drawable transparentRect = AppGlobalMgr.getResImg(R.drawable.transparent);

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeNoFlit.setBackgroundDrawable(transparentRect);
                ((TextView)timeNoFlit).setTextColor(AppGlobalMgr.getResColor(R.color.mygreylighter));

                if (lastView != null) {
                    lastView.setBackgroundDrawable(transparentRect);
                    ((TextView)lastView).setTextColor(AppGlobalMgr.getResColor(R.color.mygreylighter));

                }
                lastView = view;
                view.setBackgroundDrawable(pinkRect);
                ((TextView)view).setTextColor(AppGlobalMgr.getResColor(R.color.mygreydarker));

                setTimescope(position + 1);
            }
        });
        adapter = new FightWithAdapter(context,datas);
        if(fightWithView==null) {
            fightWithView = new ListView(context);
            AbsListView.LayoutParams layoutParams2 = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.MATCH_PARENT
            );
            fightWithView.setLayoutParams(layoutParams2);

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
                if(i==0)
                    return;
               Intent intent = new Intent((Activity) context, FightWithDetailAty.class);
                ((Activity) context).startActivity(intent);

            }
        });

        refreshLayout = (RefreshLayout) basePraent.findViewById(R.id.id_refl_fight_with);
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


        return basePraent;
    }


    public void refreshView(){

        adapter.notifyDataSetChanged();
    }

    public void getDataFromNet(boolean isRefresh) {
        this.isRefresh = isRefresh;
        final RequestParams rp = new RequestParams();
        rp.put("district", district );
        rp.put("timescpoe",timescope);
        rp.put("rule",rule);
        rp.put("listtype",listType);

        if (isRefresh) {
            refreshLayout.setRefreshing(true);
            rp.put("offset",0);
        }else{
            rp.put("offset",nowpos);

        }

        if(postHttpCilent ==null)
            postHttpCilent = new PostHttpCilent(context);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                postHttpCilent.execRequest
                        (AppConstantValue.URL_TEST_DIR + "fightlist.json", rp, new FightWithDataHandler());
            }
        },1000);



    }

    public void setDistrict(int district) {
        this.district = district;
        getDataFromNet(true);
    }

    public void setTimescope(int timescope) {
        this.timescope = timescope;
        getDataFromNet(true);

    }

    public void setRule(int rule) {
        this.rule = rule;
        getDataFromNet(true);

    }

    class  FightWithDataHandler  extends BaseJsonHttpResponseHandler<NetPackData> {


        @Override
        public void onSuccess
                (int statusCode, Header[] headers, String rawJsonResponse,NetPackData response) {

            if(response.getType()==NetPackData.TYPE_FIGHT_LIST
                    && response.getStatus()==NetPackData.STATUS_SUCC){

                //若是0的话代表没有偏移即是刷新
                nowpos = Integer.valueOf(response.getHeadOtherData());
                if(nowpos==0) {
                    datas.clear();
                }
                datas.addAll((ArrayList<FightWithData>)response.getData());

                if (refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);

                refreshLayout.setLoading(false);

                refreshView();
                LogUtil.d("加载约战列表");

            }else{
                Toast.makeText(context, "获取列表异常",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure
                (int statusCode, Header[] headers, Throwable throwable,
                 String rawJsonData, NetPackData errorResponse) {

            if (refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);

            refreshLayout.setLoading(false);

            if(statusCode!=200)
                Toast.makeText(context, "网络异常"+statusCode,Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "数据解析异常",Toast.LENGTH_LONG).show();

        }

        @Override
        protected NetPackData parseResponse
                (String rawJsonData, boolean isFailure) throws Throwable {

            if(isFailure)
                return null;

            ObjectMapper mapper = new ObjectMapper();
            JsonParser jp = new JsonFactory().createParser(rawJsonData);
            jp.nextToken();//跳过{

            NetPackData netPackData = null;
            ArrayList<FightWithData> fightWithDatasTemp = new ArrayList<FightWithData>();
            //刷新则要将数据清空
            while (jp.nextToken() != JsonToken.END_OBJECT) {

                String fieldName = jp.getCurrentName();
               /* if(fieldName==null)
                    continue;
*/
                jp.nextToken();//进入到键所对应的值的对象中

                if (fieldName.equals("head")) {
                     netPackData = mapper.readValue(jp, NetPackData.class);
                } else if (fieldName.equals("data")) {
                    if(jp.getValueAsString()!=null && jp.getValueAsString().equals("")) {
                        LogUtil.d("has null data");
                        continue;
                    }

                    while (jp.nextToken() == JsonToken.START_OBJECT) {
                        FightWithData data = mapper.readValue(jp, FightWithData.class);
                        if(data!=null)
                            fightWithDatasTemp.add(data);
                        else
                            LogUtil.d("has null data");
                    }
                } else {
                    throw new IOException("Unrecognized field '"+fieldName+"'");
                }
            }
            jp.close();


            if(netPackData!=null){
                netPackData.setData(fightWithDatasTemp);
            }

            return netPackData;
        }
    };

}
