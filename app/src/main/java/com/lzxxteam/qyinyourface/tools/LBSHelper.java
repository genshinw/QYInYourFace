package com.lzxxteam.qyinyourface.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.DistrictChildrenParam;
import com.tencent.lbssearch.object.result.DistrictResultObject;
import com.tencent.map.geolocation.TencentGeofence;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.mapsdk.raster.model.GeoPoint;

import org.apache.http.Header;

/**
 * Created by Elvis on 2015/6/16.
 */
public class LBSHelper {

    public static GeoPoint of(TencentLocation location) {
        return new GeoPoint((int) (location.getLatitude() * 1E6),
                (int) (location.getLongitude() * 1E6));
    }

    public static GeoPoint of(double latitude, double longitude) {
        return new GeoPoint((int) (latitude * 1E6),
                (int) (longitude * 1E6));
    }

    public static double fmt(double d) {
        long i = (long) (d * 1e6);
        return i / 1e6;
    }

    public static String toString(TencentGeofence geofence) {
        return geofence.getTag() + " " + geofence.getLatitude() + ","
                + geofence.getLongitude();
    }



    public void getDistrict(final Context context){
        TencentSearch api = new TencentSearch(AppGlobalMgr.getAppContext());
        final int id = 440000;//广东id
        final int GUANGZHOU_ID = 440100;
        final int SHEN_ZHEN_ID = 440300;
        final int HUIZHOU_ID = 441300;
        DistrictChildrenParam param = new DistrictChildrenParam().id(SHEN_ZHEN_ID);
        api.getDistrictChildren(param, new HttpResponseListener() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, BaseObject object) {
                // TODO Auto-generated method stub
                if(object != null){
                    String result = "根据行政区划id获取具体子行政区划，为空时返回所有省市区划\n\n";
                    result += "当前id为110000，北京市id\n";

                    DistrictResultObject oj = (DistrictResultObject)object;
                    if(oj.result != null && oj.result.size() > 0){

                        for(DistrictResultObject.DistrictResult r : oj.result.get(0)){
                            Log.v("demo", "location:" + r.fullname);
                            result += r.fullname+" "+r.id+"\n";
                        }

                    }
                    new AlertDialog.Builder(context).setTitle("行政区").setMessage(result).create().show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(context).setTitle("error").setMessage(responseString).create().show();
            }
        });
    }
}
