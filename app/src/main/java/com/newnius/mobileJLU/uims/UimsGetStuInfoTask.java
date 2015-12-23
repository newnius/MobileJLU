package com.newnius.mobileJLU.uims;

import android.os.AsyncTask;
import android.util.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newnius.mobileJLU.Config;
import com.newnius.mobileJLU.GetCurrentWeekTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by newnius on 15-12-14.
 * note: termId is also got here
 * to fix: sometimes this would get 301 response(not logged), but another app works fine. don't know why
 */
public class UimsGetStuInfoTask extends AsyncTask<Void, Void, Boolean> {
    private final String cookie;

    public UimsGetStuInfoTask(String cookie) {
        this.cookie = cookie;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            URL url = new URL("http://cjcx.jlu.edu.cn/score/action/getCurrentUserInfo.php");
            if(Config.getInCampus()){
                url = new URL("http://uims.jlu.edu.cn/ntms/action/getCurrentUserInfo.do");
            }

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Cookie", cookie);

            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String response = "";
            String readLine = null;
            while((readLine =br.readLine()) != null){
                response = response + readLine;
            }
            is.close();
            br.close();
            conn.disconnect();

            Log.i("getStuInfo and getTerms", response);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            UimsSession.setUserId(root.path("userId").asInt());
            UimsSession.setTermId(root.at("/defRes/teachingTerm").asInt());
            UimsSession.setNickName(root.path("nickName").asText());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if(success){
            new GetCurrentWeekTask().execute();
        }}
}