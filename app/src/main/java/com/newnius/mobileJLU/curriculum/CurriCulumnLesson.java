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
    private int start = 0;
    private int length = 0;
    private String weekOddEven;//E & O

    public CurriCulumnLesson(String classroomName, int classSet, int endWeek, int beginWeek, int dayOfWeek, String weekOddEven) {
        this.classroomName = classroomName;
        this.classSet = classSet;
        this.endWeek = endWeek;
        this.beginWeek = beginWeek;
        this.dayOfWeek = dayOfWeek;
        this.weekOddEven = weekOddEven;
        while(classSet>0){
            start++;
            if((classSet&1) == 1){
                length++;
            }
            classSet = classSet>>1;
        }
        start-=length;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
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

    public String getWeekOddEven() {
        return weekOddEven;
    }

    public void setWeekOddEven(String weekOddEven) {
        this.weekOddEven = weekOddEven;
    }

    public int getStart(){
        return start;
    }

    public int getLength(){
        return length;
    }
}
