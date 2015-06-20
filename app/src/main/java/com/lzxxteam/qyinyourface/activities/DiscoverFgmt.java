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

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.tools.AppGlobalMgr;
import com.lzxxteam.qyinyourface.tools.GetImageFromNet;
import com.lzxxteam.qyinyourface.ui.IndicaterViewPagerFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.solo.waterfall.WaterfallSmartView;

import java.lang.reflect.Field;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setActionBarTitle(AppGlobalMgr.getResString(R.string.fgmt_name_discover));

        viewPagerFactory = new IndicaterViewPagerFactory(atyToAttach);
        View view1 = inflater.inflate(R.layout.fgmt_discover_gyms, null);
        View view2 = inflater.inflate(R.layout.pageview3, null);
        View gymFliterView = view1.findViewById(R.id.id_ll_gyms_fliter);
        ArrayList<View> listView = new ArrayList<View>();
        listView.add(view1);
        listView.add(view2);

        viewPagerFactory.addViewPagerViews(listView, null);

        container = viewPagerFactory.getIndicaterViewPager(new String[]{"热门场地", "附近场地"});
        viewPagerFactory.showSearchBtn();

        mHandler = new Handler();
        mAdapter = new PhotoAdapter(atyToAttach);
        mWaterfall = (WaterfallSmartView) view1.findViewById(R.id.waterfall);
        mWaterfall.setAdapter(mAdapter);
        mWaterfall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(atyToAttach, GymsDetailAty.class);
                startActivity(intent);
            }
        });
        mImageLoader = GetImageFromNet.getInstance();
        mOptions = GetImageFromNet.getOptions();
        loadUrl(urls);
        return container;
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
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            mImageLoader.displayImage((String) getItem(position), holder.imageView, mOptions);
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
    }

    private void loadUrl(String[] urls) {
        for (final String url : toList(urls)) {
            mImageLoader.loadImage(url, mImageLoadingListener);
        }
    }

    // Show slowly how it work
    private void loadUrlSlow(String[] urls) {
        long time = 0L;
        for (final String url : toList(urls)) {
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mImageLoader.loadImage(url, mImageLoadingListener);
                }
            }, time);
            time += 1000L;
        }
    }

    private List<String> toList(String[] strings) {
        List<String> list = new ArrayList<String>(strings.length);
        for (String s : strings) {
            list.add(s);
        }
        return list;
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

    private static String[] urls = {
            "http://119.29.58.57/inyourface/data/appTestFile/examplegyms/gym1.png",
            "http://119.29.58.57/inyourface/data/appTestFile/examplegyms/gym2.png",
            "http://119.29.58.57/inyourface/data/appTestFile/examplegyms/gym3.png",
            "http://119.29.58.57/inyourface/data/appTestFile/examplegyms/gym4.png",
            "http://119.29.58.57/inyourface/data/appTestFile/examplegyms/gym3.png",
            "http://119.29.58.57/inyourface/data/appTestFile/examplegyms/gym2.png",
            "http://119.29.58.57/inyourface/data/appTestFile/examplegyms/gym4.png"

    };
}

