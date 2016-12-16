package com.newnius.mobileJLU.uims;

import android.util.Log;

import com.google.gson.Gson;
import com.newnius.mobileJLU.Config;
import com.newnius.mobileJLU.model.UIMSEvalItem;
import com.newnius.mobileJLU.model.UIMSEvalItemAnswer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by newnius on 12/16/16.
 *
 */
public class UIMSEvalController {
    private static List<UIMSEvalItem> items;

    public static List<UIMSEvalItem> getEvalItems() throws IOException {
        Log.i("score", "getScoreTask");

        if(items != null){// use cache
            return items;
        }

        if(!Config.getInCampus()) {
            return new ArrayList<>();
        }

        URL url = new URL("http://uims.jlu.edu.cn/ntms/service/res.do");
        String json = "{\"tag\":\"student@evalItem\",\"branch\":\"self\",\"params\":{\"blank\":\"Y\"}}";

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        //conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Cookie", UimsSession.getCookie());
        conn.connect();

        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(json);
        out.flush();
        out.close();

        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String response = "";
        String readLine;
        while((readLine =br.readLine()) != null){
            response = response + readLine;
        }
        is.close();
        br.close();
        conn.disconnect();
        Log.i("getScoreByTerm", response);

        String res_json = response;
        items = UIMSEvalItem.json2array(res_json);
        return items;
    }

    public static List<String> doOneKeyEval(){
        List<String> results = new ArrayList<>();
        try {
            getEvalItems();
            for(UIMSEvalItem item: items){
                results.add(doEval(item.getId(), new UIMSEvalItemAnswer()));
            }
        } catch (IOException e) {
            results.add("fail");
            e.printStackTrace();
        }


        return results;
    }

    public static String doEval(String evalItemId, UIMSEvalItemAnswer answer) throws IOException {
        Log.i("score", "getScoreTask");
        if(!Config.getInCampus()) {
            return "not in campus";
        }

        URL url = new URL("http://uims.jlu.edu.cn/ntms/eduEvaluate/eval-with-answer.do");
        String answer_json = new Gson().toJson(answer);
        String json = "{\"evalItemId\":\""+evalItemId+"\",\"answers\":"+answer_json+"}";

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        //conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Cookie", UimsSession.getCookie());
        conn.connect();

        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(json);
        out.flush();
        out.close();

        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String response = "";
        String readLine;
        while((readLine =br.readLine()) != null){
            response = response + readLine;
        }
        is.close();
        br.close();
        conn.disconnect();
        Log.i("getScoreByTerm", response);
        return "success";
    }
}
