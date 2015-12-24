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
 * Created by newnius on 15-12-23.
 */
public class GetRegCourseDetailTask extends AsyncTask<Void, Void, List<CurriculumCourse>>{
    private RegCourseDetailActivity regCourseDetailActivity;
    private int lslId;

    public GetRegCourseDetailTask(RegCourseDetailActivity regCourseDetailActivity, int lslId) {
        this.regCourseDetailActivity = regCourseDetailActivity;
        this.lslId = lslId;
    }

    @Override
    protected List<CurriculumCourse> doInBackground(Void... params) {
        try {
            URL url = new URL("http://uims.jlu.edu.cn/ntms/service/res.do");
            String json = "{\"tag\":\"lessonSelectLogTcm@selectGlobalStore\",\"branch\":\"default\",\"params\":{\"lslId\":\"" + lslId + "\",\"myCampus\":\"Y\"}}";
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
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
            while((readLine = br.readLine()) != null){
                response = response + readLine;
            }
            is.close();
            br.close();
            conn.disconnect();
            //Log.i("getRegCourseDetail", response);

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
                    int studCnt = teachClassMaster.path("studCnt").asInt();
                    String teacherName = teachClassMaster.path("lessonTeachers").get(0).at("/teacher/name").asText();
                    int teacherId = teachClassMaster.path("lessonTeachers").get(0).at("/teacher/teacherId").asInt();;
                    String courseName = course.at("/lessonSelectLog/lesson/courseInfo/courName").asText();
                    int lsltId = course.path("lsltId").asInt();
                    boolean isSelected = course.path("selectTag").asText().equals("Y")?true:false;
                    CurriculumCourse curriculumCourse = new CurriculumCourse( maxStudCnt,  lessons, teacherName, teacherId, courseName);
                    curriculumCourse.setLsltId(lsltId);
                    curriculumCourse.setStuCnt(studCnt);
                    curriculumCourse.setIsSelected(isSelected);
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
    protected void onPostExecute(List<CurriculumCourse> curriculumCourses) {
        if(curriculumCourses!=null)
            regCourseDetailActivity.display(curriculumCourses);
    }



}
