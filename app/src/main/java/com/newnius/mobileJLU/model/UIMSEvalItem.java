package com.newnius.mobileJLU.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by newnius on 12/16/16.
 *
 */
public class UIMSEvalItem {
    private String id;
    private String teacherName;
    private String courseName;

    public UIMSEvalItem(String id, String teacherName, String courseName) {
        this.id = id;
        this.teacherName = teacherName;
        this.courseName = courseName;
    }

    public static List<UIMSEvalItem> json2array(String json) throws IOException {
        List<UIMSEvalItem> items = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(json);
        JsonNode courses = root.path("value") ;
        int status = root.path("status").asInt();
        String msg = root.path("msg").asText();

        for (JsonNode course : courses) {
            String evalItemId = course.path("evalItemId").asText();
            String teacherName = course.at("/target/name").asText();
            String courseName = course.at("/targetClar/notes").asText();
            UIMSEvalItem item = new UIMSEvalItem(evalItemId, teacherName, courseName);
            items.add(item);
        }
        return items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
