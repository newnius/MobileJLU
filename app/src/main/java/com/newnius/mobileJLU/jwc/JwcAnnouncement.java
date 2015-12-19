package com.newnius.mobileJLU.jwc;

import java.util.List;

/**
 * Created by newnius on 15-12-18.
 */
public class JwcAnnouncement {
    private int id;
    private String title;
    private String content;
    private String date;
    private String department;
    private List<String> attachments;
    private List<String> attachmentNames;

    public JwcAnnouncement(int id, String title, String date, String department) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.department = department;
    }

    public JwcAnnouncement(int id, String title, String content, String date, String department, List<String> attachments, List<String> attachmentNames) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.department = department;
        this.attachments = attachments;
        this.attachmentNames = attachmentNames;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getDepartment() {
        return department;
    }
}
