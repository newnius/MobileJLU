package com.newnius.mobileJLU;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OA_detail extends AppCompatActivity {
    private int id;
    private Handler handler;
    private String title;
    private String publisher;
    private String time;
    private String content;
    private TextView OATitle;
    private TextView OAPublisher;
    private TextView OATime;
    private TextView OAContent;
    private List<String> downloadable = new ArrayList<>();
    private List<String> downloadableNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oa_detail);

        OATitle = (TextView)findViewById(R.id.OATitle);
        OAPublisher = (TextView)findViewById(R.id.OAPublisher);
        OATime = (TextView)findViewById(R.id.OATime);
        OAContent = (TextView)findViewById(R.id.OAContent);

        OAContent.setMovementMethod(ScrollingMovementMethod.getInstance());


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (title != null) {
                    OATitle.setText(title);
                    OAPublisher.setText(publisher);
                    OATime.setText(time);
                    OAContent.setText(content);

                }
                super.handleMessage(msg);
            }
        };


        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        Bundle bundle = intent.getExtras();
                        downloadContent("http://oa.jlu.edu.cn/" + bundle.getString("url"));
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }
        ).start();




    }

    private void downloadContent(String location) {
        Log.i("download", location);
        String result = "";
        String str;
        try {
            URL url = new URL(location);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3 * 1000);

            if(conn.getResponseCode() != 200){
                return ;
            }
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "gbk"));
            while((str = br.readLine()) != null){
                result += str;
            }
            conn.disconnect();

        }catch(Exception e){
            e.printStackTrace();
            Log.i("portalJLU", e.toString());
        }
        parse(result);
    }

    public void parse(String resource){
        // 生成一个Pattern,同时编译一个正则表达式
        Pattern p = Pattern.compile("<td align=\"center\" style=\"padding-bottom:10px;\"><span style=\"width:700px;font-size:18px;font-weight:bold;\">(.+)</span></td>");

        Matcher m = p.matcher(resource);
        if (m.find()) {
            title = m.group(1);

        }else{
            return ;
        }

        // 生成一个Pattern,同时编译一个正则表达式
        Pattern p1 = Pattern.compile("<td height=\"30\" align=\"right\" valign=\"top\">提交部门：<a href=\"([^\"]+)\">([^<]+)</a> &nbsp;&nbsp;提交时间：([^<]+)</td>");
        //用Pattern的split()方法把字符串按"/"分割
        Matcher m1 = p1.matcher(resource);
        if (m1.find()) {
            publisher = m1.group(2);
            time = m1.group(3);
        }else{
            return ;
        }


        // 生成一个Pattern,同时编译一个正则表达式
        Pattern p3 = Pattern.compile("<p>([^<]+)</p>");
        //用Pattern的split()方法把字符串按"/"分割
        Matcher m3 = p3.matcher(resource);
        content = "";
        while (m3.find()) {
            content += m3.group(1) + "\n";
        }
        content = content.replaceAll("&nbsp;","");


        // 生成一个Pattern,同时编译一个正则表达式
        Pattern p4 = Pattern.compile("<a href=\"\\.\\./\\.\\./(down\\.asp\\?id=[\\d]+&fid=[\\d+])\">([^>]+)</a>");
        //用Pattern的split()方法把字符串按"/"分割
        Matcher m4 = p4.matcher(resource);
        String downStr = "";
        String name = "";
        while (m4.find()) {
            downStr = "http://oa.jlu.edu.cn/"+m4.group(1);
            name = m4.group(2);
            downloadable.add(downStr);
            Log.i("oa", downStr + "=>" + name);
            downloadableNames.add(name);
        }


/*
* attach
* <table id="attach" width="100%" border="0" align="center"><tr><td><hr size="1" width="300" align="left" />&nbsp;&nbsp;&nbsp;&nbsp;<img align="absmiddle" src="../../image/attach.gif" /> 附件</td></tr><tr><td><ol>(.*)</ol></td></tr></table>
* */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oa_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
