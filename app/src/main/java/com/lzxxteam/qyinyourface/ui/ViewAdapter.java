package com.lzxxteam.qyinyourface.ui;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewAdapter extends PagerAdapter {
	public List<View> views = null;

	public ViewAdapter(List<View> view) {
		views = view;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1), 0);
		return views.get(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

//	@Override
//	public int getCount() {
//		return Integer.MAX_VALUE;
//	}
//
//	@Override
//	public boolean isViewFromObject(View arg0, Object arg1) {
//		return arg0 == arg1;
//	}
//
//	@Override
//	public int getItemPosition(Object object) {
//		return super.getItemPosition(object);
//	}
//
//	@Override
//	public Object instantiateItem(View arg0, int arg1) {
//		try {
//			((ViewPager) arg0).addView(views.get(arg1 % views.size()), 0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return views.get(arg1 % views.size());
//	}
//
//	@Override
//	public void destroyItem(View arg0, int arg1, Object arg2) { }
}
