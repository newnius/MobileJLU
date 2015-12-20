package com.newnius.mobileJLU.job;

import android.os.AsyncTask;
import android.util.Log;

import com.newnius.mobileJLU.JobActivity;
import com.newnius.mobileJLU.util.Announcement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by newnius on 15-12-20.
 */
public class JobGetTask extends AsyncTask<Void, Void, List<Announcement>>{
    //http://jdjyw.jlu.edu.cn/index.php?r=front/recruit/index&type=2&page=2
    private JobActivity jobActivity;
    private int pageId;
    /*
    *  1 校园招聘
    *  2 招聘快讯
    *  3 实习招聘
    *  4 基层招聘
    *
    * */
    private int type;

    public JobGetTask(JobActivity jobActivity, int type, int pageId) {
        this.jobActivity = jobActivity;
        this.type = type;
        this.pageId = pageId;
    }

    @Override
    protected List<Announcement> doInBackground(Void... params) {
        try {
            URL url = new URL("http://jdjyw.jlu.edu.cn/index.php?r=front/recruit/index&type=" + type + "&page=" + pageId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String response = "";
            String readLine;
            while ((readLine = br.readLine()) != null) {
                response = response + readLine;
            }

            is.close();
            br.close();
            conn.disconnect();
            Log.i("job", response);

            List<Announcement> announcements = new ArrayList<>();
            Pattern p = Pattern.compile("<li>[^<]+<a (class=\"f_red\")? href=\"([^\"]+)\" title=\"([^\"]+)\">[^<]+</a>[^<]+<span class=\"date\">([^&]+)&nbsp;&nbsp;([^<]+)</span>[^<]+</li>");
            Matcher m = p.matcher(response);
            while(m.find()) {
                int i = 1;
                if(m.groupCount()==5)i++;
                String aurl = m.group(i++);
                String title = m.group(i++);
                String date = m.group(i++);
                String publisher = m.group(i++);
                Announcement announcement = new Announcement(aurl, publisher, title, date);
                announcements.add(announcement);
            }
            return announcements;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<Announcement> announcements) {
        jobActivity.display(announcements);
    }
}
