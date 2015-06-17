package com.lzxxteam.qyinyourface.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.GymData;
import com.lzxxteam.qyinyourface.presenters.FightGymTimeSelControler;
import com.lzxxteam.qyinyourface.tools.LBSHelper;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapController;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.OnInforWindowClickListener;
import com.tencent.tencentmap.mapsdk.map.OnLoadedListener;
import com.tencent.tencentmap.mapsdk.map.OnMarkerPressListener;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.aty_goto_fight1);
        findViewById(R.id.id_aty_goto_fight_next1).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.id_sel_the_gym_time).setOnClickListener(new View.OnClickListener() {
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
        gymdatas.add(new GymData("西南球场",22.527560,113.931350));


        gymName = (TextView) findViewById(R.id.id_aty_goto_fight_gym_name);
        gymDesc = (TextView) findViewById(R.id.id_aty_goto_fight_gym_desc);
        mMapView = (MapView)findViewById(R.id.mapview);
        mMapView.onCreate(bundle);
        mMapView.getController().setZoom(15);
        mMapView.setScalControlsEnable(true);
        mLocationManager = TencentLocationManager.getInstance(this);
        mMapCtrler = mMapView.getController();

        mMapCtrler.setOnMarkerClickListener(new OnMarkerPressListener() {
            Marker lastMarker = null;
            @Override
            public void onMarkerPressed(Marker marker) {
                if(lastMarker!=null)
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
