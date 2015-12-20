package com.newnius.mobileJLU;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.newnius.mobileJLU.uims.UimsCourse;
import com.newnius.mobileJLU.uims.UimsGetScoreTask;
import com.newnius.mobileJLU.uims.UimsLoginActivity;
import com.newnius.mobileJLU.uims.UimsSession;
import com.newnius.mobileJLU.uims.UimsTerm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UimsActivity extends AppCompatActivity {
    Handler handler;
    Handler termHandler;
    private ListView listView;
    List<UimsCourse> uimsCourses;
    List<UimsTerm> uimsTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uims);

        try {
            if (!Config.getInCampus() && !Config.getCanAccessInternet()) {
                Toast.makeText(UimsActivity.this, "网络不通", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if (UimsSession.getCookie() == null) {
                //Toast.makeText(this, "尚未登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UimsActivity.this, UimsLoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                listView = (ListView) findViewById(R.id.listView);
                showTerms();
                getLatestScore();
            }
        }catch(Exception e){
            Log.e("uims",e.toString());
        }
    }


    public void getLatestScore(){
        new UimsGetScoreTask(this,0).execute();
    }

    public void getScoreByTerm(final int termId){
        new UimsGetScoreTask(this,termId).execute();
    }

    public void showTerms() {
        uimsTerms = Config.getTerms();
        Spinner spinner = (Spinner) findViewById(R.id.c);
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (final UimsTerm uimsTerm: uimsTerms) {
            HashMap<String, Object> item= new HashMap<>();
            item.put("termId", uimsTerm.getTermId());
            item.put("termName", uimsTerm.getTermName());
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(UimsActivity.this, data, R.layout.my_simple_spinner_item, new String[]{"termName"}, new int[]{R.id.title});

        adapter.setDropDownViewResource(R.layout.my_simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0,true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> data = (HashMap<String, Object>)parent.getItemAtPosition(position);
                Log.i("spinner", data.get("termId").toString());
                getScoreByTerm(Integer.parseInt(data.get("termId").toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void display(List<UimsCourse> uimsCourses){
        List<HashMap<String, String>> data = new ArrayList<>();
        for (final UimsCourse course : uimsCourses) {
            HashMap<String, String> item= new HashMap<>();
            item.put("courseName", course.getCourName());;
            item.put("score", course.getScore() + "");
            data.add(item);
        }
        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter = new SimpleAdapter(UimsActivity.this, data, R.layout.item_course,
                new String[]{"courseName","score"}, new int[]{R.id.courName,R.id.courseScore});
        //实现列表的显示
        listView.setAdapter(adapter);
        //条目点击事件
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //display graph or more detailed info
                    }
                });
    }

}
