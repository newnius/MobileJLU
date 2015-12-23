package com.newnius.mobileJLU.regCourse;

/**
 * Created by newnius on 15-12-23.
 */
public class RegCourse {
    private int lessonId;
    private int totalClassHour;
    private String courseName;
    private int courseId;
    private String schoolName;
    private boolean isSelected;
    private double credit;

    public RegCourse(int lessonId, int totalClassHour, String courseName, int courseId, String schoolName, boolean isSelected, double credit) {
        this.lessonId = lessonId;
        this.totalClassHour = totalClassHour;
        this.courseName = courseName;
        this.courseId = courseId;
        this.schoolName = schoolName;
        this.isSelected = isSelected;
        this.credit = credit;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int getLessonId() {
        return lessonId;
    }

    public int getTotalClassHour() {
        return totalClassHour;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public double getCredit() {
        return credit;
    }

    /*    {
        "lessonSelectLogTcms":null,
            "selectPlan":null,
            "lesson":{
                "lessonId":"30932",
                "totalClassHour":"64",
                "courseInfo":{
                    "courName":"编译原理与实现",
                    "courseId":"14616"
                },
                "teachSchool":{
                    "schoolName":"软件学院"
                }
            },
            "teachingTerm":null,
                "sumLssgCnt":"1",
                "student":null,
                "selectResult":"Y",
                "lslId":"38810013",
                "selLssgCnt":"1",
                "replyTag":null,
                "notes":null,
                "applyPlanLesson":{
                    "isReselect":"N",
                    "testForm":"3090",
                    "aplId":"143456",
                    "credit":"3.5",
                    "selectType":"3060"
                }
    }*/
}
