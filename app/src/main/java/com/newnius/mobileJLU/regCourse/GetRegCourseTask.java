package com.newnius.mobileJLU.regCourse;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newnius.mobileJLU.RegCourseActivity;
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
 * Created by newnius on 15-12-23.
 */
public class GetRegCourseTask extends AsyncTask<Void, Void, List<RegCourse>>{
    RegCourseActivity regCourseActivity;

    public GetRegCourseTask(RegCourseActivity regCourseActivity) {
        this.regCourseActivity = regCourseActivity;
    }

    @Override
    protected List<RegCourse> doInBackground(Void... params) {
        Log.i("score", "getScoreTask");
        try {
            int userId = UimsSession.getUserId();
            int planId = 600;
            URL url = new URL("http://uims.jlu.edu.cn/ntms/service/res.do");
            String json = "{\"tag\":\"lessonSelectLog@selectStore\",\"branch\":\"default\",\"params\":{\"splanId\":" + planId + ",\"studId\":"+ userId +"}}";


            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
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
            Log.i("getRegCourse", response);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            //fail
            if(root.path("status").asInt()!=0){
                return null;
            }
            List<RegCourse> regCourses = new ArrayList<>();
            JsonNode courses = root.path("value");
            for(JsonNode course:courses){
                int lessonId = course.at("/lesson/lessonId").asInt();
                int totalClassHour = course.at("/lesson/totalClassHour").asInt();
                String courseName = course.at("/lesson/courseInfo/courName").asText();
                int courseId = course.at("/lesson/courseInfo/courseId").asInt();
                String schoolName = course.at("/lesson/teachSchool/schoolName").asText();
                boolean isSelected = course.path("selectResult").asText().equals("Y")?true:false;
                Double credit = course.at("/applyPlanLesson/credit").asDouble();
                int lslId = course.path("lslId").asInt();
                RegCourse regCourse = new RegCourse(lslId, lessonId, totalClassHour, courseName, courseId, schoolName, isSelected, credit);
                regCourses.add(regCourse);
            }
            return regCourses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<RegCourse> regCourses) {
        if(regCourses!=null)
            regCourseActivity.display(regCourses);
        else
            Toast.makeText(regCourseActivity, "获取课程列表失败.", Toast.LENGTH_SHORT).show();
    }
}
