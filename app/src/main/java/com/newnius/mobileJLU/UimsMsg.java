package com.newnius.mobileJLU;

import java.util.List;

/**
 * Created by newnius on 15-12-10.
 */
public class UimsMsg {
    String id;
    String msg;
    String resName;
    List<UimsCourse> value;

    public UimsMsg(String id, List<UimsCourse> value, String resName, String msg) {
        this.id = id;
        this.value = value;
        this.resName = resName;
        this.msg = msg;
    }

    public List<UimsCourse> getValue() {
        return value;
    }

    public void setValue(List<UimsCourse> value) {
        this.value = value;
    }
}
