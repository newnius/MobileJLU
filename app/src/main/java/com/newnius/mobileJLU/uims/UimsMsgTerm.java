package com.newnius.mobileJLU.uims;

import java.util.List;

/**
 * Created by newnius on 15-12-12.
 */
public class UimsMsgTerm extends UimsMsg {
    private List<UimsTerm> value;

    public UimsMsgTerm(String id, String resName, String msg, int status, List<UimsTerm> uimsTerms) {
        super(id, resName, msg, status);
        this.value = uimsTerms;
    }

    public List<UimsTerm> getValue() {
        return value;
    }

    public void setValue(List<UimsTerm> value) {
        this.value = value;
    }
}

