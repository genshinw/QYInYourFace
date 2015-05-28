package com.lzxxteam.qyinyourface.ui;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.demievil.swiperefreshlayout.RefreshLayout;
import com.lzxxteam.qyinyourface.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithViewControler {


    private  List<FightWithData> datas;
    private  FightWithAdapter adapter;
    private Context context;
    private ListView fightWithView;
    private RefreshLayout refreshLayout;

    public FightWithViewControler(Context context,List<FightWithData> dataList){

        this.context = context;
        this.datas = dataList;
        if(datas==null){
            datas = new ArrayList<FightWithData>();
            String fightTime = "4月15日/4月20日";
            String space = "深圳体育馆/福田体育馆";
            int[] porttraits = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,
            R.drawable.p5,R.drawable.p1
            };

            for(int i=0;i<6;i++) {
                FightWithData data = new FightWithData(new UserBaseData(
                        AppGlobalMgr.getAppResources().getDrawable(porttraits[i]), "User"),
                        space,
                        fightTime
                );
                datas.add(data);
            }

        }
        adapter = new FightWithAdapter(context,datas);


    }

    public View getFightWithView() {

        if(fightWithView==null) {
            fightWithView = new ListView(context);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.MATCH_PARENT
            );
            fightWithView.setLayoutParams(layoutParams);

        }
        fightWithView.setAdapter(adapter);
        refreshLayout = new RefreshLayout(context);
        refreshLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        refreshLayout.addView(fightWithView);
        refreshLayout.setFooterView(context, fightWithView, R.layout.lv_footer_fresh);
        refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String fightTime = "4月10日/4月20日";
                        String space = "深圳体育馆/福田体育馆";
                        int[] porttraits = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4,
                                R.drawable.p5, R.drawable.p1
                        };

                        for (int i = 5; i >= 0; i--) {
                            FightWithData data = new FightWithData(new UserBaseData(
                                    AppGlobalMgr.getAppResources().getDrawable(porttraits[i]), "User"),
                                    space,
                                    fightTime
                            );
                            datas.add(data);
                            adapter.notifyDataSetChanged();
                            refreshLayout.setLoading(false);
                        }
                    }
                }, 2000);
            }
        });


        return refreshLayout;
    }

    public void refreshView(){

        adapter.notifyDataSetChanged();
    }





}
