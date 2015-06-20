package com.lzxxteam.qyinyourface.activities;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.GymData;
import com.lzxxteam.qyinyourface.presenters.FightGymTimeSelControler;
import com.lzxxteam.qyinyourface.tools.LBSHelper;
import com.lzxxteam.qyinyourface.tools.LogUtil;
import com.lzxxteam.qyinyourface.ui.ViewPagerAdapter;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapController;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.OnLoadedListener;
import com.tencent.tencentmap.mapsdk.map.OnMarkerPressListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Elvis on 2015/6/16.
 */
public class GoToFightAty extends BaseAty implements TencentLocationListener{

    private MapView mMapView;
    private TencentLocationManager mLocationManager;
    private TencentLocation mLocation;
    private ArrayList<GymData> gymdatas;
    private MapController mMapCtrler;
    private TextView gymName;
    private TextView gymDesc;
    private ViewGroup goToFightPage1,goToFightPage0;
    private ViewPager viewpager;
    private List<View> viewpagerListView;
    private ViewGroup indicatorViews;
    private String date;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.aty_goto_fight);
        indicatorViews = (ViewGroup)findViewById(R.id.id_ll_vp_indicate);
        viewpager = (ViewPager)findViewById(R.id.id_vp_aty_go_to_fight);
        viewpagerListView = new ArrayList<View>();
        viewpagerListView.add(initPage0());
        viewpagerListView.add(initPage1(bundle));
        viewpagerListView.add(initPage2());
        viewpagerListView.add(initPage3());


        viewpager.setAdapter(new ViewPagerAdapter(viewpagerListView));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            ImageView lastView = null;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(lastView!=null){
                    lastView.setImageResource(R.drawable.line_grey);
                }
                lastView  = (ImageView)indicatorViews.getChildAt(position);
                lastView.setImageResource(R.drawable.line_red);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private ViewGroup initPage0(){
        goToFightPage0 =(ViewGroup)LayoutInflater.from(this).inflate(R.layout.aty_goto_fight0,null);
        final TextView yearMonthTextView  = (TextView) goToFightPage0.findViewById(R.id.ic_tv_aty_gtf_set_date_yearmonth);
        final TextView dayTextView  = (TextView) goToFightPage0.findViewById(R.id.ic_tv_aty_gtf_set_date_day);
        goToFightPage0.findViewById(R.id.ic_ll_aty_gtf_set_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet
                                    (DatePickerDialog datePickerDialog,
                                     int year, int monthOfYear, int dayOfMonth) {
                                String month="";
                                String day = "";
                                String yearSyr ="";
                                if(++monthOfYear<10)
                                     month+="0"+monthOfYear+"";
                                else
                                    month=""+monthOfYear;


                                if(dayOfMonth<10)
                                    day+="0"+dayOfMonth;
                                else
                                    day  = ""+dayOfMonth;

                                yearSyr+= ""+year;

                                dayTextView.setText(day);
                                yearMonthTextView.setText(yearSyr+"年"+month+"日");
                                date = yearSyr.substring(yearSyr.length()-2,yearSyr.length())+month+day;
                                LogUtil.d("SelDate:"+date);

                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(),"daate");
            }
        });

        return goToFightPage0;
    }

    private ViewGroup initPage1(Bundle bundle) {
        goToFightPage1 = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.aty_goto_fight1,null);
        goToFightPage1.findViewById(R.id.id_aty_goto_fight_next1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.aty_goto_fight2);
                findViewById(R.id.id_aty_goto_fight_next1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.aty_goto_fight3);
                    }
                });
            }
        });
        goToFightPage1.findViewById(R.id.id_sel_the_gym_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FightGymTimeSelControler(GoToFightAty.this).showGymTimeDialog();
            }
        });
        gymdatas = new ArrayList<GymData>();
        //深大南区运动
        gymdatas.add(new GymData("深大南区运动",22.531268,113.939810));
        //海边游泳场
        gymdatas.add(new GymData("海边游泳场",22.531650,113.939650));
        //元平体育馆
        gymdatas.add(new GymData("元平体育馆", 22.536060, 113.938980));
        //南图书馆球场
        gymdatas.add(new GymData("南图书馆球场", 22.527480, 113.929940));
        gymdatas.add(new GymData("北图书馆篮球场", 22.533020, 113.933090));
        gymdatas.add(new GymData("西南球场", 22.527560, 113.931350));


        gymName = (TextView) goToFightPage1.findViewById(R.id.id_aty_goto_fight_gym_name);
        gymDesc = (TextView) goToFightPage1.findViewById(R.id.id_aty_goto_fight_gym_desc);
        mMapView = (MapView)goToFightPage1.findViewById(R.id.mapview);
        mMapView.onCreate(bundle);

        return goToFightPage1;
    }
    private ViewGroup initPage2(){
        return (ViewGroup)LayoutInflater.from(this).inflate(R.layout.aty_goto_fight2,null);
    }
    private ViewGroup initPage3(){
        return (ViewGroup)LayoutInflater.from(this).inflate(R.layout.aty_goto_fight3,null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.getController().setZoom(15);
        mMapView.setScalControlsEnable(true);
        mLocationManager = TencentLocationManager.getInstance(this);
        mMapCtrler = mMapView.getController();

        mMapCtrler.setOnMarkerClickListener(new OnMarkerPressListener() {
            Marker lastMarker = null;

            @Override
            public void onMarkerPressed(Marker marker) {
                if (lastMarker != null)
                    lastMarker.setIcon(BitmapDescriptorFactory.fromAsset("cur_loc.png"));

                lastMarker = marker;
                marker.setIcon(BitmapDescriptorFactory.fromAsset("loc.png"));
                gymName.setText(marker.getTitle());
                gymDesc.setText(marker.getSnippet());
            }
        });
        mMapCtrler.setOnMapLoadedListener(new OnLoadedListener() {
            @Override
            public void onMapLoaded() {
                startLocation();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
        if (error == TencentLocation.ERROR_OK) {
            mLocation = tencentLocation;
            // 更新 location 图层
            mMapView.getController().animateTo(LBSHelper.of(mLocation),400,null);
            for(int i=0;i<gymdatas.size();i++) {
                GymData gymData = gymdatas.get(i);
                Marker markerFix = mMapView.addMarker(new MarkerOptions()
                        .position(gymData.getLatLng())
                        .title(gymData.getName())
                        .snippet("室内,位于深圳大学("+gymData.getLat()+","+gymData.getLng()+")")
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromAsset("cur_loc.png"))
                        .draggable(false));
            }
            stopLocation();
        }
    }
    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
    private void startLocation() {

        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(5000);
        mLocationManager.requestLocationUpdates(request, this);


    }

    private void stopLocation() {
        mLocationManager.removeUpdates(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocation();
    }


    /*  class LocationOverlay extends Overlay {

        GeoPoint geoPoint;
        Bitmap bmpMarker;
        float fAccuracy = 0f;

        public LocationOverlay(Bitmap mMarker) {
            bmpMarker = mMarker;
        }

        public void setGeoCoords(GeoPoint point) {
            if (geoPoint == null) {
                geoPoint = new GeoPoint(point.getLatitudeE6(),
                        point.getLongitudeE6());
            } else {
                geoPoint.setLatitudeE6(point.getLatitudeE6());
                geoPoint.setLongitudeE6(point.getLongitudeE6());
            }
        }

        public void setAccuracy(float fAccur) {
            fAccuracy = fAccur;
        }

        @Override
        public void draw(Canvas canvas, MapView mapView) {
            if (geoPoint == null) {
                return;
            }
            Projection mapProjection = mapView.getProjection();
            Paint paint = new Paint();
            Point ptMap = mapProjection.toPixels(geoPoint, null);
            paint.setColor(Color.BLUE);
            paint.setAlpha(8);
            paint.setAntiAlias(true);

            float fRadius = mapProjection.metersToEquatorPixels(fAccuracy);
            canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);
            canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);

            if (bmpMarker != null) {
                paint.setAlpha(255);
                canvas.drawBitmap(bmpMarker, ptMap.x - bmpMarker.getWidth() / 2,
                        ptMap.y - bmpMarker.getHeight() / 2, paint);
            }

            super.draw(canvas, mapView);
        }
    }*/


}
