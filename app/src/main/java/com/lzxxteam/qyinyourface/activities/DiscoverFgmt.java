package com.lzxxteam.qyinyourface.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.FightWithData;
import com.lzxxteam.qyinyourface.model.GymData;
import com.lzxxteam.qyinyourface.model.NetPackData;
import com.lzxxteam.qyinyourface.net.PostHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppConstantValue;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.tools.GetImageFromNet;
import com.lzxxteam.qyinyourface.tools.LogUtil;
import com.lzxxteam.qyinyourface.ui.IndicaterViewPagerFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.Header;
import org.solo.waterfall.WaterfallSmartView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvis on 2015/6/11.
 */
public class DiscoverFgmt extends BaseFgmt {


    private ViewGroup viewPager;
    private IndicaterViewPagerFactory viewPagerFactory;
    private PhotoAdapter mAdapter;
    private ImageLoader mImageLoader;
    private WaterfallSmartView mWaterfall;
    private DisplayImageOptions mOptions;
    private Handler mHandler;
    private PostHttpCilent postHttpClient;
    ArrayList<GymData> gymListDatas = new ArrayList<GymData>();
    private int nowpos = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setActionBarTitle(AppGlobalMgr.getResString(R.string.fgmt_name_discover));

//        viewPagerFactory = new IndicaterViewPagerFactory(atyToAttach);
        View gymListView = inflater.inflate(R.layout.fgmt_discover_gyms, null);
        View gymFliterView = gymListView.findViewById(R.id.id_ll_gyms_fliter);
        gymListView.findViewById(R.id.action_load_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromNet();
            }
        });


      /*  ArrayList<View> listView = new ArrayList<View>();
        listView.add(view1);
        listView.add(view2);

        viewPagerFactory.addViewPagerViews(listView, null);

        container = viewPagerFactory.getIndicaterViewPager(new String[]{"热门场地", "附近场地"});
*/
        mHandler = new Handler();
        mAdapter = new PhotoAdapter(atyToAttach);
        mWaterfall = (WaterfallSmartView) gymListView.findViewById(R.id.waterfall);
        mWaterfall.setAdapter(mAdapter);
      mWaterfall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(atyToAttach, GymsDetailAty.class);
                intent.putExtra("gymId",gymListDatas.get(position).getId());
                startActivity(intent);
            }
        });

        mImageLoader = GetImageFromNet.getInstance();
        mOptions = GetImageFromNet.getOptions();
        postHttpClient = new PostHttpCilent(atyToAttach);
        getDataFromNet();
        return gymListView;
    }
    private void getDataFromNet(){


        RequestParams rps = new RequestParams();
        rps.put("offset", nowpos);
        rps.put("district", 0);
        postHttpClient.execRequest("fight/courtlist", rps, new BaseJsonHttpResponseHandler<NetPackData>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, NetPackData response) {
                if(response.getType()==NetPackData.TYPE_GYM_LIST
                        && response.getStatus()==NetPackData.STATUS_SUCC){
                    for(int i=nowpos;i<gymListDatas.size();i++) {
                        mImageLoader.loadImage(
                                "http://"+
                                AppConstantValue.URL_SERVER +
                                        AppConstantValue.URL_TEST_DIR +
                                        "profile/" +
                                        (gymListDatas.get(i).getId()%30+1) + "c.png",
                                mImageLoadingListener
                        );
                    }

                    nowpos = Integer.valueOf(response.getHeadOtherData());


                    LogUtil.d("加载约战列表");

                }else if(response.getType()==207) {
                    Toast.makeText(atyToAttach, "没有数据", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(atyToAttach, "获取列表异常",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, NetPackData errorResponse) {

            }

            @Override
            protected NetPackData parseResponse(String rawJsonData, boolean isFailure) throws Throwable {


                ObjectMapper mapper = new ObjectMapper();
                JsonParser jp = new JsonFactory().createParser(rawJsonData);
                jp.nextToken();//跳过{

                NetPackData netPackData = null;

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
                            GymData data = mapper.readValue(jp, GymData.class);
                            if(data!=null)
                                gymListDatas.add(data);
                            else
                                LogUtil.d("has null data");
                        }
                    } else {
                        throw new IOException("Unrecognized field '"+fieldName+"'");
                    }
                }
                jp.close();


                if(netPackData!=null){
                    netPackData.setData(gymListDatas);
                }
                return netPackData;

            }
        });

    }
    class PhotoAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;

        public PhotoAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.photo_item, parent, false);
                holder.imageView = (ImageView) convertView.findViewById(R.id.id_gym_list_photo);
                holder.nameTextView = (TextView) convertView.findViewById(R.id.id_gym_list_name);
                holder.addrTextView = (TextView) convertView.findViewById(R.id.id_gym_list_addr);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final GymData gymData = gymListDatas.get(position);
            if(gymData!=null) {
                mImageLoader.displayImage((String) getItem(position),holder.imageView,mOptions );

                holder.nameTextView.setText(gymData.getName());
                holder.addrTextView.setText(gymData.getGymAddr());
            }

            return convertView;
        }

        public void add(String object, int weight, int height) {
            super.add(object);
            // AddItem to waterfall in same time
            mWaterfall.addItem(object, weight, height);
        }


    }

    class ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView addrTextView;
    }




    ImageLoadingListener mImageLoadingListener = new ImageLoadingListener() {

        @Override
        public void onLoadingStarted(String imageUri, View view) {
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        }

        @Override
        public void onLoadingComplete(final String imageUri, View view, Bitmap loadedImage) {
            mAdapter.add(imageUri, loadedImage.getWidth(), loadedImage.getHeight());
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
        }


    };

    

}

