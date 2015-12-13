package com.newnius.mobileJLU.uims;

/**
 * Created by newnius on 15-12-12.
 */
public class UimsStuInfo {
    private String nickName;
    private int userId;
    private String userType;
    private String welcome;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    /*{
        "defRes": {
            "adcId": 4837,
            "campus": 1401,
            "department": 4,
            "personId": 232059,
            "schType": 1450,
            "school": 101,
            "teachingTerm": 129,
            "term_a": 130,
            "term_l": 129,
            "term_s": 129,
            "university": 1,
            "year": 2015
        },
        "firstLogin": "Y",
        "groupsInfo": [
            {
            "groupId": 6,
            "groupName": "学生",
            "menuFile": "STUDENT.json"
            }
        ],
        "groupsName": [
            "学生"
        ],
        "loginMethod": "SIMPLE",
        "loginName": "54130523",
        "menusFile": [
            "STUDENT.json"
        ],
        "nickName": "于佳欣",
        "options": {},
        "sysTime": "2015-12-12T19:02:22",
        "trulySch": 101,
        "userId": 232059,
        "userType": "S",
        "welcome": "welcome"
    }
*/

}
