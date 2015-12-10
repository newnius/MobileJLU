package com.newnius.mobileJLU;

import android.util.Log;

/**
 * Created by newnius on 15-12-10.
 */
public class Course {
    private int activeStatus;
    private double adviceCredit;
    private int adviceHour;
    private int batch;
    private String courName;
    private int courseId;
    private String englishName;
    private String extCourseNo;
    private int type5;

    public Course(int activeStatus, double adviceCredit, int adviceHour, int batch, String courName, int courseId, String englishName, String extCourseNo, int type5) {
        this.activeStatus = activeStatus;
        this.adviceCredit = adviceCredit;
        this.adviceHour = adviceHour;
        this.batch = batch;
        this.courName = courName;
        this.courseId = courseId;
        this.englishName = englishName;
        this.extCourseNo = extCourseNo;
        this.type5 = type5;
    }

    /*    "activeStatus": "103",
            "adviceCredit": "2",
            "adviceHour": "32",
            "batch": "13",
            "courName": "组合数学",
            "courseId": "14385",
            "englishName": "Combinatorial Mathematics",
            "extCourseNo": "ac13541012",
            "type5": "4161"*/

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCourName() {
        return courName;
    }

    public void setCourName(String courName) {
        this.courName = courName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}

