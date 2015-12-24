package com.newnius.mobileJLU.curriculum;

import java.util.List;

/**
 * Created by newnius on 15-12-13.
 */
public class CurriculumCourse {
    private int lsltId;
    private int maxStudCnt;
    private int stuCnt;
    private List<CurriCulumnLesson> lessons;
    private String teacherName;
    private int teacherId;
    private String courseName;
    private boolean isSelected;

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

    public int getStuCnt() {
        return stuCnt;
    }

    public void setStuCnt(int stuCnt) {
        this.stuCnt = stuCnt;
    }

    public int getLsltId() {
        return lsltId;
    }

    public void setLsltId(int lsltId) {
        this.lsltId = lsltId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
