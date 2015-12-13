package com.newnius.mobileJLU.uims;

import java.util.List;

/**
 * Created by newnius on 15-12-10.
 */
public class UimsMsg {
    String id;
    String msg;
    String resName;
    int status;

    public UimsMsg(String id, String resName, String msg, int status) {
        this.id = id;
        this.resName = resName;
        this.msg = msg;
        this.status = status;
    }
}
