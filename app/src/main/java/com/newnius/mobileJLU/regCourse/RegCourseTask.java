package com.newnius.mobileJLU.regCourse;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newnius.mobileJLU.curriculum.CurriCulumnLesson;
import com.newnius.mobileJLU.curriculum.CurriculumCourse;
import com.newnius.mobileJLU.uims.UimsSession;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by newnius on 15-12-24.
 */
public class RegCourseTask extends AsyncTask<Void, Integer, Boolean>{
    private RegCourseDetailActivity regCourseDetailActivity;
    private boolean isSelect;
    private int errno;
    private String msg;
    private int lsltId;
    private int maxRepeatTime = 3;// max times to try select course

    public RegCourseTask(RegCourseDetailActivity regCourseDetailActivity, boolean isSelect, int lsltId, boolean autoRepeat) {
        this.regCourseDetailActivity = regCourseDetailActivity;
        this.isSelect = isSelect;
        this.lsltId = lsltId;
        if(!autoRepeat)maxRepeatTime=1;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        int repeatCnt = 0;

        try {
            while(repeatCnt++ < maxRepeatTime) {
                String opType = isSelect ? "Y" : "N";
                URL url = new URL("http://uims.jlu.edu.cn/ntms/selectlesson/select-lesson.do");
                String json = "{\"lsltId\":\"" + lsltId + "\",\"opType\":\"" + opType + "\"}";
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
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
                while ((readLine = br.readLine()) != null) {
                    response = response + readLine;
                }
                is.close();
                br.close();
                conn.disconnect();
                //Log.i("getRegCourseDetail", response);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response);
                errno = root.path("errno").asInt();
                msg = root.path("msg").asText();
                publishProgress(repeatCnt);
                if(errno==0) break;
            }
            return errno==0;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if(errno==0)return; // do not report progress if success
            Log.i("repeatCnt", progress[0]+": "+ msg);
    }


    @Override
    protected void onPostExecute(Boolean success) {
        if(success != null){
            regCourseDetailActivity.displaySelectResult(msg);
        }
    }
}
