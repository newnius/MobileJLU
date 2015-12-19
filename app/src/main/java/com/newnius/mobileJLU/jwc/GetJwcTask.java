package com.newnius.mobileJLU.jwc;

import android.os.AsyncTask;
import android.util.Log;
import com.newnius.mobileJLU.JwcActivity;
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
 * Created by newnius on 15-12-18.
 */
public class GetJwcTask extends AsyncTask<Void, Void, List<JwcAnnouncement>>{
    private JwcActivity jwcActivity;
    private int pageId;

    public GetJwcTask(JwcActivity jwcActivity, int pageId) {
        this.jwcActivity = jwcActivity;
        this.pageId = pageId;
    }

    @Override
    protected List<JwcAnnouncement> doInBackground(Void... params) {
        try {
            URL url = new URL("http://jwc.jlu.edu.cn/?file=info&act=list&id=28&page="+pageId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "gb2312"));
            String response = "";
            String readLine = null;
            while((readLine =br.readLine()) != null){
                response = response + readLine;
            }

            is.close();
            br.close();
            conn.disconnect();
            //Log.i("jwc", response);

            List<JwcAnnouncement> announcements = new ArrayList<JwcAnnouncement>();
            // 生成一个Pattern,同时编译一个正则表达式
            Pattern p = Pattern.compile("<li><span class=\"right\">\\[([-0-9]+)\\]</span> <a href=\"\\?file=info&act=view&id=([0-9]+)\">([^>]+)(<span class=\"new\">new</span>)?</a><span class=\"dep\">\\(<a href=\"\\?file=info&act=search&kw=\\(all\\)&st=28&dp=([^\"]+)\">([^<]+)</a>\\)</span></li>");
            //用Pattern的split()方法把字符串按"/"分割
            Matcher m = p.matcher(response);
            while (m.find()) {
                int no = 1;
                String date = m.group(no++);
                int id = Integer.parseInt(m.group(no++));
                String title = m.group(no++);
                if(m.groupCount()==6){
                    no++;//skip <span class="new">new</span>
                }
                String department = m.group(no++);
                //Log.i("jwc", m.group(no));//department
                JwcAnnouncement announcement = new JwcAnnouncement(id, title, date, department);
                announcements.add(announcement);
            }
            return announcements;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<JwcAnnouncement> announcements) {
        if(announcements!=null)
            jwcActivity.display(announcements);
    }


/*
    */
}
