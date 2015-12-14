package com.newnius.mobileJLU.curriculum;

import android.os.AsyncTask;
import android.util.Log;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newnius.mobileJLU.CurriculumActivity;
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
 * Created by newnius on 15-12-13.
 *
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class CurriculumGetTask extends AsyncTask<Void, Void, List<CurriculumCourse>>{
        private final int termId;
        private final CurriculumActivity curriculumActivity;

    public CurriculumGetTask(CurriculumActivity curriculumActivity,int termId) {
        this.curriculumActivity = curriculumActivity;
        this.termId = termId;
    }

        @Override
        protected List<CurriculumCourse> doInBackground(Void... params) {
            String cookie = UimsSession.getCookie();
            int userId = UimsSession.getUserId();
                    try {
                        URL url = new URL("http://uims.jlu.edu.cn/ntms/service/res.do");
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("Cookie", cookie);
                        conn.connect();

                        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes("{\"tag\":\"teachClassStud@schedule\",\"branch\":\"default\",\"params\":{\"termId\":"+ termId +",\"studId\":"+userId+"}}");
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
                        Log.i("getCurriculumn", response);

                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode root = objectMapper.readTree(response);
                        JsonNode value = root.path("value");

                        List<CurriculumCourse> curriculumCourses = new ArrayList<>();
                        if (value.isArray())
                        {
                            for (JsonNode course : value)
                            {
                                JsonNode teachClassMaster = course.path("teachClassMaster");
                                JsonNode lessonSchedules = teachClassMaster.path("lessonSchedules");
                                List<CurriCulumnLesson> lessons = new ArrayList<>();
                                for (JsonNode lessonSchedule : lessonSchedules){
                                    String classroomName = lessonSchedule.at("/classroom/fullName").asText();
                                    int classSet = lessonSchedule.at("/timeBlock/classSet").asInt();
                                    int endWeek = lessonSchedule.at("/timeBlock/endWeek").asInt();
                                    int beginWeek = lessonSchedule.at("/timeBlock/beginWeek").asInt();
                                    int dayOfWeek = lessonSchedule.at("/timeBlock/dayOfWeek").asInt();
                                    String weekOddEven = lessonSchedule.at("/timeBlock/weekOddEven").asText();
                                    CurriCulumnLesson lesson = new CurriCulumnLesson(classroomName, classSet, endWeek,beginWeek,dayOfWeek,weekOddEven);
                                    lessons.add(lesson);
                                }

                                int maxStudCnt = teachClassMaster.path("maxStudCnt").asInt();
                                String teacherName = teachClassMaster.path("lessonTeachers").get(0).at("/teacher/name").asText();
                                int teacherId = teachClassMaster.path("lessonTeachers").get(0).at("/teacher/teacherId").asInt();;
                                String courseName = teachClassMaster.at("/lessonSegment/fullName").asText();
                                CurriculumCourse curriculumCourse = new CurriculumCourse( maxStudCnt,  lessons, teacherName, teacherId, courseName);
                                curriculumCourses.add(curriculumCourse);
                            }
                        }

                        return curriculumCourses;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
        }

    @Override
    protected void onCancelled() {
    /*    mAuthTask = null;
        showProgress(false);
    */}

        @Override
        protected void onPostExecute(List<CurriculumCourse> courses) {
            curriculumActivity.display(courses);
        /*    mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        */}
}
