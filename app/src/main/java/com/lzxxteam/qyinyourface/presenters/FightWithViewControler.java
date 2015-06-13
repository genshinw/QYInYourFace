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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.activities.FightWithDetailAty;
import com.lzxxteam.qyinyourface.model.FightWithData;
import com.lzxxteam.qyinyourface.net.GetHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.tools.LogMsgUtil;
import com.lzxxteam.qyinyourface.ui.FightWithAdapter;
import com.lzxxteam.qyinyourface.ui.RefreshLayout;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Elvis on 15/5/22.
 */
public class FightWithViewControler {


    private int touchSlop;
    private  ArrayList<FightWithData> datas;
    private FightWithAdapter adapter;
    private Context context;
    private ListView fightWithView;
    private RefreshLayout refreshLayout;
    private ViewGroup basePraent;
    private View filterView;
    private View header;

    public FightWithViewControler(Context context){

        this.context = context;
        datas = new ArrayList<FightWithData>();
        //滚动过多少距离后才开始计算是否隐藏/显示头尾元素。这里用了默认touchslop的0.9倍。
        touchSlop = (int) (ViewConfiguration.get(context).getScaledTouchSlop() * 0.9);
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

        fightWithView.setOnTouchListener(onTouchListener);
        fightWithView.setOnScrollListener(onScrollListener);
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

//        basePraent.addView(refreshLayout);
        return basePraent;
    }

    public void getDataFromNet(final boolean isRefresh) {
        new GetHttpCilent(context).execRequest("testFightWith.json", new BaseJsonHttpResponseHandler<ArrayList<FightWithData>>() {
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

                while (jp.nextToken() == JsonToken.START_OBJECT) {

                    datas.add(mapper.readValue(jp, FightWithData.class));
                }
                return datas;
            }
        });
    }

    public void refreshView(){

        adapter.notifyDataSetChanged();
    }

    AnimatorSet backAnimatorSet;//这是显示头尾元素使用的动画
    AnimatorSet hideAnimatorSet;//这是隐藏头尾元素使用的动画

    private void animateBack() {
        //先清除其他动画
        if (hideAnimatorSet != null && hideAnimatorSet.isRunning()) {
            hideAnimatorSet.cancel();
        }
        if (backAnimatorSet != null && backAnimatorSet.isRunning()) {
            //如果这个动画已经在运行了，就不管它
        } else {
            backAnimatorSet = new AnimatorSet();
            //下面两句是将头尾元素放回初始位置。
            ObjectAnimator headerAnimator = ObjectAnimator.ofFloat(filterView, "translationY", filterView.getTranslationY(), 0f);


            ArrayList<Animator> animators = new ArrayList<>();
            animators.add(headerAnimator);
            backAnimatorSet.setDuration(300);
            backAnimatorSet.playTogether(animators);
            backAnimatorSet.start();
        }
    }
    private void animateHide() {
        //先清除其他动画
        if (backAnimatorSet != null && backAnimatorSet.isRunning()) {
            backAnimatorSet.cancel();
        }
        if (hideAnimatorSet != null && hideAnimatorSet.isRunning()) {
            //如果这个动画已经在运行了，就不管它
        } else {
            hideAnimatorSet = new AnimatorSet();
            ObjectAnimator headerAnimator = ObjectAnimator.ofFloat(filterView, "translationY", filterView.getTranslationY(), -filterView.getHeight());//将ToolBar隐藏到上面
            ArrayList<Animator> animators = new ArrayList<>();
            animators.add(headerAnimator);
            hideAnimatorSet.setDuration(200);
            hideAnimatorSet.playTogether(animators);
            hideAnimatorSet.start();
        }
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {


        float lastY = 0f;
        float currentY = 0f;
        //下面两个表示滑动的方向，大于0表示向下滑动，小于0表示向上滑动，等于0表示未滑动
        int lastDirection = 0;
        int currentDirection = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastY = event.getY();
                    currentY = event.getY();
                    currentDirection = 0;
                    lastDirection = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (fightWithView.getFirstVisiblePosition() > 0) {
                        //只有在listView.getFirstVisiblePosition()>0的时候才判断是否进行显隐动画。因为listView.getFirstVisiblePosition()==0时，
                        //ToolBar——也就是头部元素必须是可见的，如果这时候隐藏了起来，那么占位置用了headerview就被用户发现了
                        //但是当用户将列表向下拉露出列表的headerview的时候，应该要让头尾元素再次出现才对——这个判断写在了后面onScrollListener里面……
                        float tmpCurrentY = event.getY();
                        if (Math.abs(tmpCurrentY - lastY) > touchSlop) {//滑动距离大于touchslop时才进行判断
                            currentY = tmpCurrentY;
                            currentDirection = (int) (currentY - lastY);
                            if (lastDirection != currentDirection) {
                                //如果与上次方向不同，则执行显/隐动画
                                if (currentDirection < 0) {
                                    animateHide();
                                } else {
                                    animateBack();
                                }
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    //手指抬起的时候要把currentDirection设置为0，这样下次不管向哪拉，都与当前的不同（其实在ACTION_DOWN里写了之后这里就用不着了……）
                    currentDirection = 0;
                    lastDirection = 0;
                    break;
            }
            return false;
        }
    };



    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

        //这个Listener其实是用来对付当用户的手离开列表后列表仍然在滑动的情况，也就是SCROLL_STATE_FLING

        int lastPosition = 0;//上次滚动到的第一个可见元素在listview里的位置——firstVisibleItem
        int state = SCROLL_STATE_IDLE;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //记录当前列表状态
            state = scrollState;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0) {
                animateBack();
            }
            if (firstVisibleItem > 0) {
                if (firstVisibleItem > lastPosition && state == SCROLL_STATE_FLING) {
                    //如果上次的位置小于当前位置，那么隐藏头尾元素
                    animateHide();
                }

                //================================
                if (firstVisibleItem < lastPosition && state == SCROLL_STATE_FLING) {
                    //如果上次的位置大于当前位置，那么显示头尾元素，其实本例中，这个if没用
                    //如果是滑动ListView触发的，那么，animateBack()肯定已经执行过了，所以没有必要
                    //如果是点击按钮啥的触发滚动，那么根据设计原则，按钮肯定是头尾元素之一，所以也不需要animateBack()
                    //所以这个if块是不需要的
                    animateBack();
                }
                //这里没有判断(firstVisibleItem == lastPosition && state == SCROLL_STATE_FLING)的情况，
                //但是如果列表中的单个item如果很长的话还是要判断的，只不过代码又要多几行
                //但是可以取巧一下，在触发滑动的时候拖动执行一下animateHide()或者animateBack()——本例中的话就写在那个点击事件里就可以了）
                //BTW，如果列表的滑动纯是靠手滑动列表，而没有类似于点击一个按钮滚到某个位置的话，只要第一个if就够了…

            }
            lastPosition = firstVisibleItem;
        }
    };

}
