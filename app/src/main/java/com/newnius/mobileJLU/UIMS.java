package com.newnius.mobileJLU;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UIMS extends AppCompatActivity {
    Handler handler;
    private ListView listView;
    List<UimsCourse> uimsCourses;
    String username = "54130507";
    String password = "a810286392";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uims);


        listView = (ListView)findViewById(R.id.listView);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<HashMap<String, String>> data = new ArrayList<>();
                    for (final UimsCourse course : uimsCourses) {
                        HashMap<String, String> item= new HashMap<>();
                        item.put("courseName", course.getCourse().getCourName());
                        course.getCourse().getCourName();
                        item.put("score", course.getScore() + "");
                        data.add(item);
                    }
                    //创建SimpleAdapter适配器将数据绑定到item显示控件上
                    SimpleAdapter adapter = new SimpleAdapter(UIMS.this, data, R.layout.item_course,
                            new String[]{"courseName","score"}, new int[]{R.id.courName,R.id.courseScore});
                    //实现列表的显示
                    listView.setAdapter(adapter);
                    //条目点击事件
                    listView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*                                    ListView listView = (ListView) parent;
                                    HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                                    String url = data.get("url").toString();

                                    Intent intent = new Intent(UIMS.this, OA_detail.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putCharSequence("url", url);
                                    intent.putExtras(bundle);
                                    startActivity(intent);*/
                                }
                            });
                super.handleMessage(msg);
            }
        };

        login(username, password);
    }

    public void get(final String location,final String cookie){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sourceCode = "";
                String str = null;
                try {
                    URL url = new URL(location);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(3 * 1000);
                    conn.setRequestProperty("Cookie",cookie);

                    Log.i("uims", conn.getHeaderField("set-cookie"));
                    Map map = conn.getHeaderFields();
                    for (Iterator i = map.keySet().iterator(); i.hasNext();) {
                        Object obj = i.next();
                        System.out.println("key=" + obj + " value=" + map.get(obj));
                    }
                    if(conn.getResponseCode() != 200){
                        return ;
                    }
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    while((str = br.readLine()) != null){
                        sourceCode += str;
                    }
                    Log.i("res", sourceCode);
                    conn.disconnect();

                }catch(Exception e){
                    e.printStackTrace();
                    Log.i("http", e.toString());
                }
            }
        }).start();
    }

    public boolean login(String username, String password){
        password = md5("UIMS" + username + password);
        final String data = "j_username="+username+"&j_password="+password;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://uims.jlu.edu.cn/ntms/j_spring_security_check");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);
                    conn.setInstanceFollowRedirects(false);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.connect();

                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(data);
                    out.flush();
                    out.close();

                    InputStream is = conn.getInputStream();

                    Log.i("jj", conn.getHeaderField("Set-Cookie"));
                    if(conn.getHeaderField("Location").contains("index.do")){
                        getLatestScore(conn.getHeaderField("Set-Cookie"));
                    }
                    is.close();
                    conn.disconnect();
                    return ;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();


        return true;
    }

    public void post(final String location, final String data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(location);
                    HttpURLConnection uRLConnection = (HttpURLConnection)url.openConnection();
                    uRLConnection.setDoInput(true);
                    uRLConnection.setDoOutput(true);
                    uRLConnection.setRequestMethod("POST");
                    uRLConnection.setUseCaches(false);
                    uRLConnection.setInstanceFollowRedirects(false);
                    uRLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    uRLConnection.connect();

                    DataOutputStream out = new DataOutputStream(uRLConnection.getOutputStream());
                    out.writeBytes(data);
                    out.flush();
                    out.close();

                    InputStream is = uRLConnection.getInputStream();


/*                    Map map = uRLConnection.getHeaderFields();
                    for (Iterator i = map.keySet().iterator(); i.hasNext();) {
                        Object obj = i.next();
                        System.out.println("key=" + obj + " value=" + map.get(obj));
                    }*/

                    Log.i("jj", uRLConnection.getHeaderField("Location"));
                    Log.i("jj", uRLConnection.getHeaderField("Set-Cookie"));
                    if(uRLConnection.getHeaderField("Location").contains("index.do")){
                        post("", uRLConnection.getHeaderField("Set-Cookie"));
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String response = "";
                    String readLine = null;
                    while((readLine =br.readLine()) != null){
                        response = response + readLine;
                    }
                    is.close();
                    br.close();
                    uRLConnection.disconnect();
                    Log.i("uims_login", response);
                    return ;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();
    }

    public void getLatestScore(final String cookie){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://uims.jlu.edu.cn/ntms/service/res.do");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);
                    conn.setInstanceFollowRedirects(false);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Cookie",cookie);
                    conn.connect();

                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes("{\"tag\":\"archiveScore@queryCourseScore\",\"branch\":\"latest\",\"params\":{},\"rowLimit\":15}");
                    out.flush();
                    out.close();

                    InputStream is = conn.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String response = "";
                    String readLine = null;
                    while((readLine =br.readLine()) != null){
                        response = response + readLine;
                    }
                    is.close();
                    br.close();
                    conn.disconnect();

                    UimsMsg uimsMsg = new Gson().fromJson(response, UimsMsg.class);

/*                    List<UimsCourse> list = new Gson().fromJson(new Gson().toJson(uimsMsg.getValue()), new TypeToken<List<UimsCourse>>() {}.getType());
                    uimsMsg.setValue(list);
                    uimsCourses = list;*/
                    uimsCourses = uimsMsg.getValue();

                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                    return ;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();
    }


    private String md5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {

        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        ////16位加密，从第9位到25位
        return md5StrBuff.toString().toLowerCase();
    }
}
