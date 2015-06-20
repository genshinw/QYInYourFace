package com.lzxxteam.qyinyourface.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.param.DistrictChildrenParam;
import com.tencent.lbssearch.object.result.Address2GeoResultObject;
import com.tencent.lbssearch.object.result.DistrictResultObject;
import com.tencent.map.geolocation.TencentGeofence;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.mapsdk.raster.model.GeoPoint;

import org.apache.http.Header;

import java.util.ArrayList;

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



    public static void  getDistrict(final Context context,final DealerAfterGetDistrict dealer ){
        TencentSearch api = new TencentSearch(AppGlobalMgr.getAppContext());
        final int id = 440000;//广东id
        final int GUANGZHOU_ID = 440100;
        final int SHEN_ZHEN_ID = 440300;
        final int HUIZHOU_ID = 441300;
        LogUtil.d("isError0");

        DistrictChildrenParam param = new DistrictChildrenParam().id(GUANGZHOU_ID);
        api.getDistrictChildren(param, new HttpResponseListener() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, BaseObject object) {
                // TODO Auto-generated method stub
                LogUtil.d("isError");
                if(object != null){

                    DistrictResultObject oj = (DistrictResultObject)object;
                    ArrayList<String> districtList = new ArrayList<String>();
                    if(oj.result != null && oj.result.size() > 0){

                        for(DistrictResultObject.DistrictResult r : oj.result.get(0)){
                            districtList.add(r.fullname);
                            Log.v("demo", "location:" + r.fullname+r.id);
                        }
                        dealer.dealer(districtList);

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                LogUtil.d("isError2");

                new AlertDialog.Builder(context).setTitle("error").setMessage(responseString).create().show();
            }
        });
    }


    /**
     * 地址解析(地址转坐标)
     * 本接口提供由地址描述到所述位置坐标的转换，与逆地址解析的过程正好相反。
     *
     * 需要构建Address2GeoParam对象
     * address 地址
     * region 指定地址所属城市
     * */
    public static void address2geo
    (Context context,final String address,final String region, final DealterAfterAddr2Geo dealer){
        TencentSearch api = new TencentSearch(context);
        Address2GeoParam param = new Address2GeoParam().address(address).region(region);
        api.address2geo(param, new HttpResponseListener() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, BaseObject object) {
                // TODO Auto-generated method stub
                if (object != null) {
                    Address2GeoResultObject oj = (Address2GeoResultObject) object;
                    String result = "地址转坐标：地址:" + address + "  region:" + region + "\n\n";
                    if (oj.result != null) {
                        result += oj.result.location.toString();
                        LogUtil.d("demo", "location:" + result);

                        dealer.afterSucc(oj.result.location.lat,oj.result.location.lng);

                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
            }
        });
    }












    public interface DealerAfterGetDistrict{
        public  void dealer(ArrayList<String> distrcitArray);
    }

    public interface DealterAfterAddr2Geo{
        public void afterSucc(float fat, float lng );
        public void afterFail(int statuscode );
    }
}
