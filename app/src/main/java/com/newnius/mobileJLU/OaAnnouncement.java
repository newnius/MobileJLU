package com.newnius.mobileJLU;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newnius on 15-9-12.
 */
public class OaAnnouncement {
    private int id;
    private boolean isSticked = false;
    private String url;
    private String title;
    private String publisherUrl;
    private String publisher;
    private String time;
    private List<String> downloadable;

    public OaAnnouncement(String url, String title, String publisherUrl, String publisher, String time) {


        this.title = title;
        this.publisherUrl = publisherUrl;
        this.publisher = publisher;
        this.url = url;
        this.time = time;
        this.downloadable = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDownloadable(List<String> downloadable){
        this.downloadable=downloadable;
    }

    public List<String> getDownloadable(){
        return downloadable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSticked() {
        return isSticked;
    }

    public void setIsSticked(boolean isSticked) {
        this.isSticked = isSticked;
    }
}
