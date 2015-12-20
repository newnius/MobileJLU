package com.newnius.mobileJLU;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.newnius.mobileJLU.jwc.GetJwcTask;
import com.newnius.mobileJLU.jwc.JwcAnnouncement;
import com.newnius.mobileJLU.jwc.JwcDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JwcActivity extends AppCompatActivity {
    private int currentPage = 1;
    List<HashMap<String, Object>> data = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwc);
        new GetJwcTask(this,currentPage).execute();
        Toast.makeText(JwcActivity.this,"加载中",Toast.LENGTH_SHORT).show();

        listView = (ListView)findViewById(R.id.listView);
        Button next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                new GetJwcTask(JwcActivity.this,currentPage).execute();
            }
        });

    }

    public void display(List<JwcAnnouncement> announcements){

        Toast.makeText(JwcActivity.this, announcements.size() + "", Toast.LENGTH_LONG).show();
        for (JwcAnnouncement announcement : announcements) {
            HashMap<String, Object> item= new HashMap<>();
            item.put("id", announcement.getId());
            item.put("title", announcement.getTitle());
            item.put("date", announcement.getDate());
            data.add(item);
        }
        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter = new SimpleAdapter(JwcActivity.this, data, R.layout.item_oa,
                new String[]{"title", "date"}, new int[]{R.id.oaTitle, R.id.oaTime});
        //实现列表的显示
        listView.setAdapter(adapter);
        //条目点击事件
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {
                        ListView listView = (ListView) parent;
                        HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                        String id = data.get("id").toString();
                        Intent intent = new Intent(JwcActivity.this, JwcDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", Integer.parseInt(id));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
    }
}
