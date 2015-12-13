package com.newnius.mobileJLU;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newnius on 15-12-12.
 */
public class UimsSchedule {

    public void generate(int classSet) {
        List<Integer> ts = new ArrayList<>();
        for (int i = 1; classSet != 0; i++) {
            classSet = classSet >> 1;
            if ((classSet & 1) == 1) {
                ts.add(new Integer(i));
            }
        }
        return ;
    }

   /* {
        "dateAccept": "2015-06-17T12:28:46",
        "student": null,
        "tcsId": "3857052",
        "teachClassMaster": {
            "lessonSchedules": [
            {
                "classroom": {
                    "fullName": "前卫-经信教学楼#F区第四阶梯",
                    "roomId": "304"
                },
                "lsschId": "63867",
                "timeBlock": {
                    "beginWeek": "9",
                    "classSet": "480",
                    "dayOfWeek": "6",
                    "endWeek": "16",
                    "name": "周六第5,6,7,8节{第9-16周}",
                    "tmbId": "7758"
                }
            }
            ],
            "lessonSegment": {
                "fullName": "XML",
                "lesson": {
                    "courseInfo": {
                        "courName": "XML"
                    }
                },
                "lssgId": "25199"
            },
            "lessonTeachers": [
            {
                "teacher": {
                    "name": "黄春飞",
                    "teacherId": "1867"
                }
            }
            ],
            "maxStudCnt": "256",
            "name": "(2015-2016-1)-ac13541014-600440-1",
            "studCnt": "150",
            "tcmId": "45773"
        }
    }*/
}
