package com.newnius.mobileJLU.oa;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by newnius on 15-9-12.
 *
 */
public class Spider extends Thread {
    private Handler handler;
    private String url;
    public Spider(String url, Handler handler){
        this.handler = handler;
        this.url = url;
    }

    @Override
    public void run() {
        super.run();
        try {
            String sourceCode = downloadHttp("");


        }catch(Exception e){
            Log.i("http", e.toString());
        }

    }

    private String downloadHttp(String location){
        String sourceCode = "";
        String str = null;
        try {
            URL url = new URL(location);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3*1000);
            if(conn.getResponseCode() != 200){
                return "";
            }
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while((str = br.readLine()) != null){
                sourceCode += str;
            }
            sourceCode = null;
            conn.disconnect();

        }catch(Exception e){
            e.printStackTrace();
            Log.i("http", e.toString());
        }
        return sourceCode;
    }
}
