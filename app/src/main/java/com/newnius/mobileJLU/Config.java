package com.newnius.mobileJLU;


import android.util.Log;

import com.newnius.mobileJLU.uims.UimsTerm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by newnius on 15-12-16.
 */
public class Config {

    private static Boolean inCampus;
    private static Boolean canAccessInternet;
    private static List<UimsTerm> terms;


    public static void init(){
        //load customized file
        //modules

        //check network environment
        checkIfInCampus();
        checkCanAccessInternet();
    }

    public static Boolean getInCampus() {
        return inCampus;
    }

    public static void setInCampus(Boolean inCampus) {
        Config.inCampus = inCampus;
    }

    public static Boolean getCanAccessInternet() {
        return canAccessInternet;
    }

    public static void setCanAccessInternet(Boolean canAccessInternet) {
        Config.canAccessInternet = canAccessInternet;
    }

    public static List<UimsTerm> getTerms() {
        return terms;
    }

    public static void setTerms(List<UimsTerm> terms) {
        Config.terms = terms;
    }

    private static void checkIfInCampus(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        String ip = "oa.jlu.edu.cn";
                        Process p = Runtime.getRuntime().exec("ping -c 3 -w 8 " + ip);// ping for 3 times
                        int status = p.waitFor();
                        if (status == 0) {
                            //Log.i("ping", "can visit oa");
                            inCampus = true;
                        } else {
                            inCampus = false;
                            //Log.i("ping", "can not visit oa");
                        }
                        Thread.sleep(30000);
                    } catch (Exception e) {
                        inCampus = false;
                        Log.i("ping", "error");
                        return;
                    }
                }
            }
        }).start();
    }

    private static void checkCanAccessInternet(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        String ip = "www.upyun.com";
                        Process p = Runtime.getRuntime().exec("ping -c 3 -w 10 " + ip);// ping for 3 times, wait 6s at last
                        // ping的状态
                        int status = p.waitFor();
                        if (status == 0) {
                        //    Log.i("ping", "can visit upyun");
                            canAccessInternet = true;
                        } else {
                            canAccessInternet = false;
                          //  Log.i("ping", "can not visit upyun");
                        }
                        Thread.sleep(10000);
                    }catch(Exception e){
                        canAccessInternet = false;
                        Log.i("ping", "error");
                        return;
                    }
                }
            }
        }).start();
    }
}
