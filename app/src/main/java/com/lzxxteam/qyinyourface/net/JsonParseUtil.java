package com.lzxxteam.qyinyourface.net;

import com.lzxxteam.qyinyourface.ui.FightWithData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Elvis on 15/5/22.
 */
public class JsonParseUtil {



    public ArrayList<FightWithData> parseJSONToList(String jsonData){

        ArrayList<FightWithData> dataList = new ArrayList<FightWithData>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonItem = (JSONObject)jsonArray.get(i);
                String picData = jsonItem.getString("picUrl");
               // new FightWithData(new UserBaseData())；
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
