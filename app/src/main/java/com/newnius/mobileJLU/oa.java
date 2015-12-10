package com.newnius.mobileJLU;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class oa extends AppCompatActivity {


    private Handler handler;
    private String result;
    private ListView listView;
    private int currentPage = 1;

    List<HashMap<String, Object>> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oa);

        listView = (ListView)findViewById(R.id.listView);


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (result != null) {
                    List<OA_Announcement> res = getAnnouncement(result);
                    Toast.makeText(oa.this, res.size() + "", Toast.LENGTH_LONG).show();

                    for (final OA_Announcement an : res) {
                        HashMap<String, Object> item= new HashMap<>();
                        item.put("url", an.getUrl());
                        item.put("title", an.getTitle());
                        item.put("time", an.getTime());
                        data.add(item);
                    }
                    //创建SimpleAdapter适配器将数据绑定到item显示控件上
                    SimpleAdapter adapter = new SimpleAdapter(oa.this, data, R.layout.item_oa,
                            new String[]{"title", "time"}, new int[]{R.id.oaTitle, R.id.oaTime});
                    //实现列表的显示
                    listView.setAdapter(adapter);
                    //条目点击事件
                    listView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    ListView listView = (ListView) parent;
                                    HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                                    String url = data.get("url").toString();

                                    Intent intent = new Intent(oa.this, OA_detail.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putCharSequence("url", url);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });

                }
                super.handleMessage(msg);
            }
        };

        Button button = (Button)findViewById(R.id.oaNext);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadNextPage();
                    }
                }
        );


        this.loadNextPage();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_oa clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadNextPage(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        downloadHttp("http://oa.jlu.edu.cn/list.asp?s=1&page="+currentPage);
                        currentPage++;
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }
        ).start();
    }

    public void myClick(View view){
        finish();
    }

    public Bitmap getPic(String path){
        Bitmap bm = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            bm = BitmapFactory.decodeStream(in);
            //in.close();
            //conn.disconnect();

        }catch(Exception e){
            e.printStackTrace();
            Log.i("portalJLU", e.toString());
        }
        return bm;
    }

    public void downloadHttp(String location){
        Log.i("download", location);
        result = "";
        String str = null;
        try {
            URL url = new URL(location);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3 * 1000);

            if(conn.getResponseCode() != 200){
                return;
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

    }

    public List<OA_Announcement> getAnnouncement(String resource){
        List<OA_Announcement> list = new ArrayList<OA_Announcement>();
        // 生成一个Pattern,同时编译一个正则表达式
        Pattern p = Pattern.compile("<a href=\"([^\"]+)\" title=\"([^\"]+)\">([^<]*)</a></td><td align=\"right\" class=\"listTD2\"><a href=\"([^\"]+)\">([^<]+)</a></td><td class=\"listTD[34]\">([^<]+)</td></tr>");
        //用Pattern的split()方法把字符串按"/"分割
        Matcher m = p.matcher(resource);
        while (m.find()) {
            String url = m.group(1);
            String fullTitle = m.group(2);
            String title = m.group(3);
            String publisherUrl = m.group(4);
            String publisher = m.group(5);
            String time = m.group(6);
            list.add(new OA_Announcement(url, fullTitle, publisherUrl, publisher, time));
        }


        return list;
    }

}
