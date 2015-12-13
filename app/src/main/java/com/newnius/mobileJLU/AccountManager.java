package com.newnius.mobileJLU;

/**
 * Created by newnius on 15-12-11.
 */
public class AccountManager {
    private static String classNo;
    private static String stuNo;
    private static String eduEmail;
    private static String cardNo;
    private static String UImsPassword;
    private static String UimsCookie;

    private static String name;


    public static String getUimsCookie() {
        return UimsCookie;
    }

    public static void setUimsCookie(String uimsCookie) {
        UimsCookie = uimsCookie;
    }

    public static String getStuNo() {
        return stuNo;
    }

    public static void setStuNo(String stuNo) {
        AccountManager.stuNo = stuNo;
    }

    public static String getUImsPassword() {
        return UImsPassword;
    }

    public static void setUImsPassword(String UImsPassword) {
        AccountManager.UImsPassword = UImsPassword;
    }

    public static String getClassNo() {
        return classNo;
    }

    public static void setClassNo(String classNo) {
        AccountManager.classNo = classNo;
    }

    public static String getEduEmail() {
        return eduEmail;
    }

    public static void setEduEmail(String eduEmail) {
        AccountManager.eduEmail = eduEmail;
    }

    public static String getCardNo() {
        return cardNo;
    }

    public static void setCardNo(String cardNo) {
        AccountManager.cardNo = cardNo;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        AccountManager.name = name;
    }
}
