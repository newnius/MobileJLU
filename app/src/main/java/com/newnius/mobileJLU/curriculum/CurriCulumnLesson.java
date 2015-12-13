package com.newnius.mobileJLU.curriculum;

/**
 * Created by newnius on 15-12-13.
 */
public class CurriCulumnLesson {
    private String classroomName;
    private int classSet;
    private int endWeek;
    private int beginWeek;
    private int dayOfWeek;

    public CurriCulumnLesson(String classroomName, int classSet, int endWeek, int beginWeek, int dayOfWeek) {
        this.classroomName = classroomName;
        this.classSet = classSet;
        this.endWeek = endWeek;
        this.beginWeek = beginWeek;
        this.dayOfWeek = dayOfWeek;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public int getClassSet() {
        return classSet;
    }

    public void setClassSet(int classSet) {
        this.classSet = classSet;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getBeginWeek() {
        return beginWeek;
    }

    public void setBeginWeek(int beginWeek) {
        this.beginWeek = beginWeek;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
