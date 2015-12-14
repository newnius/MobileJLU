package com.newnius.mobileJLU.uims;

/**
 * Created by newnius on 15-12-14.
 */
public class UimsSession {
    private static String nickName;
    private static String cookie;
    private static int classNo;
    private static String password;
    private static int userId;
    private static int termId;
    private static int week;
    private static String startDate;
    private static String vacationDate;


    public static String getCookie() {
        return cookie;
    }

    public static void setCookie(String cookie) {
        UimsSession.cookie = cookie;
    }

    public static int getClassNo() {
        return classNo;
    }

    public static void setClassNo(int classNo) {
        UimsSession.classNo = classNo;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UimsSession.password = password;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        UimsSession.userId = userId;
    }

    public static int getTermId() {
        return termId;
    }

    public static void setTermId(int termId) {
        UimsSession.termId = termId;
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        UimsSession.nickName = nickName;
    }

    public static int getWeek() {
        return week;
    }

    public static void setWeek(int week) {
        UimsSession.week = week;
    }

    public static String getStartDate() {
        return startDate;
    }

    public static void setStartDate(String startDate) {
        UimsSession.startDate = startDate;
    }

    public static String getVacationDate() {
        return vacationDate;
    }

    public static void setVacationDate(String vacationDate) {
        UimsSession.vacationDate = vacationDate;
    }
}
