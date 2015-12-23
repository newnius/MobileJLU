package com.newnius.mobileJLU.uims;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newnius.mobileJLU.Config;
import com.newnius.mobileJLU.UimsActivity;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by newnius on 15-12-17.
 */
public class UimsGetScoreTask extends AsyncTask<Void, Void, List<UimsCourse>>{
    private UimsActivity uimsActivity;
    private int termId = 0;

    public UimsGetScoreTask(UimsActivity uimsActivity, int termId) {
        this.uimsActivity = uimsActivity;
        this.termId = termId;
    }

    @Override
    protected List<UimsCourse> doInBackground(Void... params) {
        Log.i("score", "getScoreTask");
        try {
            int userId = UimsSession.getUserId();
            URL url = new URL("http://cjcx.jlu.edu.cn/score/action/service_res.php");
            String json;
            if(termId==0) {
                //get all
                json = "{\"tag\":\"lessonSelectResult@oldStudScore\",\"params\":{\"xh\":\"" + UimsSession.getClassNo() + "\"}}";//all terms
            }else{
                //get by termId
                json = "{\"tag\":\"lessonSelectResult@oldStudScore\",\"params\":{\"xh\":\""+UimsSession.getClassNo()+"\",\"termId\":"+termId+"}}";
            }
            if(Config.getInCampus()){
                url = new URL("http://uims.jlu.edu.cn/ntms/service/res.do");
                if(termId==0) {
                    //get latest
                    json = "{\"tag\":\"archiveScore@queryCourseScore\",\"branch\":\"latest\",\"params\":{},\"rowLimit\":15}";
                }else{
                    //get by termId
                    json = "{\"tag\":\"archiveScore@queryCourseScore\",\"branch\":\"byTerm\",\"params\":{\"studId\":" + userId + ",\"termId\":" + termId + "},\"orderBy\":\"teachingTerm.termId, course.courName\"}";
                }
            }

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
            String readLine = null;
            while((readLine =br.readLine()) != null){
                response = response + readLine;
            }
            is.close();
            br.close();
            conn.disconnect();
            Log.i("getScoreByTerm", response);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode courses;
            List<UimsCourse> uimsCourses = new ArrayList<>();

            if(Config.getInCampus()){
                courses = root.path("value");
                for(JsonNode course:courses){
                    int asId = course.path("asId").asInt();
                    int courseId = course.at("/course/courseId").asInt();
                    String courName = course.at("/course/courName").asText();
                    String englishName = course.at("/course/englishName").asText();
                    String score = course.path("score").asText();
                    Double scoreNum = course.path("scoreNum").asDouble();
                    Double credit = course.path("credit").asDouble();
                    Double gpoint = course.path("gpoint").asDouble();
                    String isPass = course.path("isPass").asText();
                    String isReselect = course.path("isReselect").asText();
                    int classHour = course.path("classHour").asInt();
                    UimsCourse uimsCourse = new UimsCourse(asId, courseId, courName, englishName, score, scoreNum, credit, gpoint, isPass, classHour, isReselect);
                    uimsCourses.add(uimsCourse);
                }
            }else{
                courses = root.path("items");
                for(JsonNode course:courses){
                    int courseId = course.path("lsrId").asInt();
                    String courName = course.path("kcmc").asText();
                    String englishName = course.path("kcmc").asText();
                    String score = course.path("cj").asText();
                    Double scoreNum = course.path("zscj").asDouble();
                    Double credit = course.path("credit").asDouble();
                    Double gpoint = course.path("gpoint").asDouble();
                    String isPass = "";
                    String isReselect = course.path("isReselect").asText();
                    int classHour = course.path("classHour").asInt();
                    UimsCourse uimsCourse = new UimsCourse(0, courseId, courName, englishName, score, scoreNum, credit, gpoint, isPass, classHour, isReselect);
                    uimsCourses.add(uimsCourse);
                }
                Collections.reverse(uimsCourses);
            }
            return uimsCourses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<UimsCourse> courses) {
        if(courses==null){
            Toast.makeText(uimsActivity, "获取成绩信息失败",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(uimsActivity, "获取到"+ courses.size() +"条成绩",Toast.LENGTH_SHORT).show();
            uimsActivity.display(courses);
        }
    }
}
