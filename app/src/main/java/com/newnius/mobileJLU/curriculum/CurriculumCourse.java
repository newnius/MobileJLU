package com.newnius.mobileJLU.curriculum;

import java.util.List;

/**
 * Created by newnius on 15-12-13.
 */
public class CurriculumCourse {
    private int maxStudCnt;
    private List<CurriCulumnLesson> lessons;
    private String teacherName;
    private int teacherId;
    private String courseName;

    public CurriculumCourse(int maxStudCnt, List<CurriCulumnLesson> lessons, String teacherName, int teacherId, String courseName) {
        this.maxStudCnt = maxStudCnt;
        this.lessons = lessons;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.courseName = courseName;
    }

    public int getMaxStudCnt() {
        return maxStudCnt;
    }

    public void setMaxStudCnt(int maxStudCnt) {
        this.maxStudCnt = maxStudCnt;
    }

    public List<CurriCulumnLesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<CurriCulumnLesson> lessons) {
        this.lessons = lessons;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
