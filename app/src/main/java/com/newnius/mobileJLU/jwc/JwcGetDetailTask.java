package com.newnius.mobileJLU.jwc;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by newnius on 15-12-19.
 */
public class JwcGetDetailTask extends AsyncTask<Void, Void, JwcAnnouncement> {
    private JwcDetailActivity jwcDetailActivity;
    private int id;

    public JwcGetDetailTask(JwcDetailActivity jwcDetailActivity, int id) {
        this.jwcDetailActivity = jwcDetailActivity;
        this.id = id;
    }

    @Override
    protected JwcAnnouncement doInBackground(Void... params) {
        try {
            URL url = new URL("http://jwc.jlu.edu.cn/?file=info&act=view&id="+id);
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

            Pattern p = Pattern.compile("<h4>([^<]+)</h4>");
            Matcher m = p.matcher(response);
            String title="";
            if (m.find()) {
                title = m.group(1);
            }

            Pattern p1 = Pattern.compile("<p class=\"info\">([^ ]+) 发表于([-: 0-9]+)  &nbsp;点击：([0-9]+)</p>");
            Matcher m1 = p1.matcher(response);
            String department="";
            String date="";
            if (m1.find()) {
                department = m1.group(1);
                date = m1.group(2);
                int hit = Integer.parseInt(m1.group(3));
            }


            Pattern p3 = Pattern.compile("<p class=\"info\">[^<]+</p>[^<]+([\\s\\S]+)<!--底部版权信息-->");
            Matcher m3 = p3.matcher(response);
            String content="";
            if (m3.find()) {
                content = m3.group(1);
            }
            content = content.replaceAll("/files/","http://jwc.jlu.edu.cn/files/");
            //content = content.replaceAll("<[^>]+>","").replaceAll("&nbsp;","");

/*
            Pattern p4 = Pattern.compile("<a href=\"/files/([^\"]+)\">([^<]+)</a>");
            Matcher m4 = p4.matcher(response);
            String downStr = "";
            String name = "";
            List<String> attachments = new ArrayList<>();
            List<String> attachmentNames = new ArrayList<>();
            while (m4.find()) {
                downStr = "http://jwc.jlu.edu.cn/files/" + m4.group(1);
                name = m4.group(2);
                attachments.add(downStr);
                Log.i("jwcActivity", downStr + " => " + name);
                attachmentNames.add(name);
                content += "<br/>附件 " + "<a href='" + downStr + "'>" + name + "</a><br/>";
            }*/

            JwcAnnouncement announcement = new JwcAnnouncement(id, title, content, date, department, null, null);
            return announcement;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JwcAnnouncement announcement) {
        if(announcement!=null)
            jwcDetailActivity.display(announcement);
    }

}
