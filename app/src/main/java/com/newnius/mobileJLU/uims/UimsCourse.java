package com.newnius.mobileJLU.uims;

/**
 * Created by newnius on 15-12-10.
 */
public class UimsCourse {
    private int asId;
    private int classHour;
    private Course course;
    private double credit;
    private String dataScore;
    private double gpoint;
    private String isPass;
    private String isReselect;
    private String notes;
    private String planDetail;
    private String score;
    private int scoreNum;
    private int selectType;
    private UimsStudent student;
    private UimsTeachingTerm teachingTerm;
    private int type5;
    private String xkkh;

    public UimsCourse(int asId, int classHour, Course course, double credit, String dataScore, double gpoint, String isPass, String isReselect, String notes, String planDetail, String score, int scoreNum, int selectType, UimsStudent student, UimsTeachingTerm teachingTerm, int type5, String xkkh) {
        this.asId = asId;
        this.classHour = classHour;
        this.course = course;
        this.credit = credit;
        this.dataScore = dataScore;
        this.gpoint = gpoint;
        this.isPass = isPass;
        this.isReselect = isReselect;
        this.notes = notes;
        this.planDetail = planDetail;
        this.score = score;
        this.scoreNum = scoreNum;
        this.selectType = selectType;
        this.student = student;
        this.teachingTerm = teachingTerm;
        this.type5 = type5;
        this.xkkh = xkkh;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
