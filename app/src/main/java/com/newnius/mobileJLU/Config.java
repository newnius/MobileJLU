package com.newnius.mobileJLU;


import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by newnius on 15-12-16.
 */
public class Config {

    private static Boolean inCampus;
    private static Boolean canAccessInternet;

    public static void init(){
        //load customized file
        //modules

        //check network environment
        checkIfInCampus();
        checkCanAccessInternet();
    }

    private static void checkIfInCampus(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ip = "oa.jlu.edu.cn";// ping 的地址，可以换成任何一种可靠的外网
                    Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
                    // 读取ping的内容，可以不加
                    InputStream input = p.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String content = "";
                    while ((content = in.readLine()) != null) {
                        stringBuffer.append(content);
                    }
                    Log.d("------ping-----", "result content : " + stringBuffer.toString());
                    // ping的状态
                    int status = p.waitFor();
                    if (status == 0) {
                        inCampus = true;
                    } else {
                        inCampus = false;
                    }
                } catch (Exception e) {
                    inCampus = false;
                    return;
                }
            }
        }).start();
    }

    private static void checkCanAccessInternet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
                    Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
                    // 读取ping的内容，可以不加
                    InputStream input = p.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(input));
                    StringBuffer stringBuffer = new StringBuffer();
                    String content = "";
                    while ((content = in.readLine()) != null) {
                        stringBuffer.append(content);
                    }
                    Log.d("------ping-----", "result content : " + stringBuffer.toString());
                    // ping的状态
                    int status = p.waitFor();
                    if (status == 0) {
                        canAccessInternet = true;
                    } else {
                        canAccessInternet = false;
                    }
                } catch (Exception e) {
                    canAccessInternet = false;
                    return;
                }
            }
        }).start();
    }
}
