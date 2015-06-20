package com.lzxxteam.qyinyourface.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.lzxxteam.qyinyourface.R;
import com.lzxxteam.qyinyourface.model.GymData;
import com.lzxxteam.qyinyourface.model.NetPackData;
import com.lzxxteam.qyinyourface.net.GetHttpCilent;
import com.lzxxteam.qyinyourface.tools.AppConstantValue;
import com.lzxxteam.qyinyourface.tools.LBSHelper;
import com.lzxxteam.qyinyourface.tools.LogUtil;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapController;
import com.tencent.tencentmap.mapsdk.map.MapView;

import org.apache.http.Header;

import java.io.IOException;

/**
 * Created by Elvis on 2015/6/14.
 */
public class GymsDetailAty extends BaseAty{

    private MapView mMapView;
    private MapController mMapCtrler;
    private TextView gymNameTextView;
    private TextView gymAddrTextView,gymPhoneTextView;
    private GetHttpCilent getHttpClient;
    private int gymId;
    private GymData gymData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_gyms_detail);

        gymNameTextView = (TextView)findViewById(R.id.id_map_aty_gym_detail_name);
        gymAddrTextView = (TextView)findViewById(R.id.id_map_aty_gym_detail_addr);
        gymPhoneTextView = (TextView)findViewById(R.id.id_map_aty_gym_detail_phone);

        gymId = getIntent().getIntExtra("gymId",0);
        
        
        mMapView = (MapView)findViewById(R.id.id_map_aty_gym_detail);
        mMapView.onCreate(savedInstanceState);
        mMapView.getController().setZoom(18);
        mMapView.setScalControlsEnable(true);
        mMapCtrler = mMapView.getController();
        
        getHttpClient = new GetHttpCilent(this);
        getDataFromNet();



    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
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

    public void getDataFromNet(){
        getHttpClient.execRequest("fight/getCourt/" + gymId, null,
                new BaseJsonHttpResponseHandler<NetPackData>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, NetPackData response) {
                        if(response.getType()==NetPackData.TYPE_GYM_DETAIl
                                && response.getStatus()==NetPackData.STATUS_SUCC){
                            gymNameTextView.setText(gymData.getName());
                            gymAddrTextView.setText(gymData.getGymAddr());
                            gymPhoneTextView.setText(gymData.getGymDesc());

                            LBSHelper.address2geo(GymsDetailAty.this, gymData.getGymAddr(), "深圳市", new LBSHelper.DealterAfterAddr2Geo() {
                                @Override
                                public void afterSucc(float fat, float lng) {
                                    gymData.setLat(fat);
                                    gymData.setLng(lng);
                                    Marker markerFix = mMapView.addMarker(new MarkerOptions()
                                            .position(gymData.getLatLng())
                                            .title(gymData.getName())
                                            .snippet(gymData.getGymDesc())
                                            .anchor(0.5f, 0.5f)
                                            .icon(BitmapDescriptorFactory.fromAsset("loc.png"))
                                            .draggable(false));
                                    mMapView.getController().animateTo(LBSHelper.of(fat,lng));
                                }

                                @Override
                                public void afterFail(int statuscode) {

                                }
                            });
                            LogUtil.d("成功");

                        }else if(response.getType()==207) {
                            Toast.makeText(GymsDetailAty.this, "没有数据", Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(GymsDetailAty.this, "获取列表异常",Toast.LENGTH_LONG).show();
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

                            jp.nextToken();//进入到键所对应的值的对象中

                            if (fieldName.equals("data")) {
                                if(jp.getValueAsString()!=null && jp.getValueAsString().equals("")) {
                                    LogUtil.d("has null data");
                                    continue;
                                }

                                while (jp.nextToken() == JsonToken.START_OBJECT) {
                                    gymData = mapper.readValue(jp, GymData.class);
                                    break;

                                }
                            } else  if (fieldName.equals("head")) {
                                netPackData = mapper.readValue(jp, NetPackData.class);
                            }
                            else {
                                throw new IOException("Unrecognized field '"+fieldName+"'");
                            }
                        }
                        jp.close();


                        if(netPackData!=null){
                            netPackData.setData(gymData);
                        }
                        return netPackData;
                    }
                });

    }
}
