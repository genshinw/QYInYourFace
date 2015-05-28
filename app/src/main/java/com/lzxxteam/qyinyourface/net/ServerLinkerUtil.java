package com.lzxxteam.qyinyourface.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Elvis on 15/5/22.
 */
public class ServerLinkerUtil {

    public static void getDataFromNet(final String url){

        new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(5000);
                    if(conn.getResponseCode()==200){
                        InputStream is = conn.getInputStream();

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
