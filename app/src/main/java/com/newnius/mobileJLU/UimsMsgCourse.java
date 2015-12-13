package com.newnius.mobileJLU;

import java.util.List;

/**
 * Created by newnius on 15-12-12.
 */
public class UimsMsgCourse extends UimsMsg{
    List<UimsCourse> value;

    public UimsMsgCourse(String id, String resName, String msg, int status, List<UimsCourse> uimsCourses) {
        super(id, resName, msg, status);
        this.value = uimsCourses;
    }

    public List<UimsCourse> getValue() {
        return value;
    }

    public void setValue(List<UimsCourse> value) {
        this.value = value;
    }
}
