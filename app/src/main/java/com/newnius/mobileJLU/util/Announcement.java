package com.newnius.mobileJLU.util;

import java.util.List;

/**
 * Created by newnius on 15-12-20.
 */
public class Announcement {
    private String url;
    private String publisher;
    private String content;
    private String date;
    private String title;
    private List<String> attachments;
    private List<String> attachmentNames;

    public Announcement(String url, String publisher, String title, String date) {
        this.url = url;
        this.publisher = publisher;
        this.title = title;
        this.date = date;
    }

    public Announcement(String url, String publisher, String content, String date, String title) {
        this.url = url;
        this.publisher = publisher;
        this.content = content;
        this.date = date;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAttachmentNames() {
        return attachmentNames;
    }

    public void setAttachmentNames(List<String> attachmentNames) {
        this.attachmentNames = attachmentNames;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
