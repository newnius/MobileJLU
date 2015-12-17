package com.newnius.mobileJLU.uims;

/**
 * Created by newnius on 15-12-10.
 */
public class UimsCourse {
    private int courseId;
    private String courName;
    private String englishName;
    private String score;//score displayed
    private double scoreNum;//score
    private double credit;// course weight
    private double gpoint;//gp
    private String isPass;
    private int classHour;
    private String isReselect;

    public UimsCourse(int courseId, String courName, String englishName, String score, double scoreNum, double credit, double gpoint, String isPass, int classHour, String isReselect) {
        this.courseId = courseId;
        this.courName = courName;
        this.englishName = englishName;
        this.score = score;
        this.scoreNum = scoreNum;
        this.credit = credit;
        this.gpoint = gpoint;
        this.isPass = isPass;
        this.classHour = classHour;
        this.isReselect = isReselect;
    }

    public String getCourName() {
        return courName;
    }

    public String getScore() {
        return score;
    }

    public double getGpoint() {
        return gpoint;
    }

    public String getIsPass() {
        return isPass;
    }
}
