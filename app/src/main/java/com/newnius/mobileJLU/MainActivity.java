package com.newnius.mobileJLU;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.newnius.mobileJLU.uims.UimsLoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
Intent intent =new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);

        loadModules();
        Config.init();

        findViewById(R.id.me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(MainActivity.this, UimsLoginActivity.class);
                startActivity(intentLogin);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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

    private void loadModules(){
        int[] moduleIcons = {R.drawable.icon_oa, R.drawable.icon_oa,R.drawable.icon_library,
                R.drawable.icon_card,R.drawable.icon_job,R.drawable.icon_mark,
                R.drawable.icon_curriculum,R.drawable.icon_link,R.drawable.icon_lost,
                R.drawable.icon_oa};
        String[] moduleIds = {"oa", "jwc", "library", "card", "job", "uims", "curriculumSchedule", "link", "lost", "map","news"};
        String[] moduleNames = {"校内通知", "教务通知", "图书服务", "校园卡", "就业信息", "成绩查询", "课程表", "吉大黄页", "失物招领", "校园地图","吉大新闻"};
        GridView gridView = (GridView)findViewById(R.id.gridView);
        List<HashMap<String, Object>> modules = new ArrayList<>();



            for (int i=0;i<9;i++) {
                HashMap<String, Object> item= new HashMap<>();
                item.put("moduleId",moduleIds[i]);
                item.put("moduleIcon", moduleIcons[i]);
                item.put("moduleName", moduleNames[i]);
                modules.add(item);
            }

            //创建SimpleAdapter适配器将数据绑定到item显示控件上
            SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, modules, R.layout.module,
                    new String[]{"moduleIcon", "moduleName"}, new int[]{R.id.moduleIcon, R.id.moduleName});
            //实现列表的显示
            gridView.setAdapter(adapter);
            //条目点击事件
            gridView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            GridView gridView = (GridView) parent;
                            HashMap<String, Object> data = (HashMap<String, Object>) gridView.getItemAtPosition(position);

                            if(Config.getCanAccessInternet()==null || Config.getInCampus()==null){
                                Toast.makeText(MainActivity.this, "环境初始化中，请3s后重试",Toast.LENGTH_SHORT).show();
                                return;
                            }

                            switch(data.get("moduleId").toString()){
                                case "oa":
                                    Intent intentOa = new Intent(MainActivity.this, OaActivity.class);
                                    startActivity(intentOa);
                                    break;
                                case "curriculumSchedule":
                                    Intent intentCurriculum = new Intent(MainActivity.this, CurriculumActivity.class);
                                    startActivity(intentCurriculum);
                                    break;

                                case "uims":
                                    Intent intentUIms = new Intent(MainActivity.this, UimsActivity.class);
                                    startActivity(intentUIms);
                                    break;

                                default:
                                    Toast.makeText(MainActivity.this,"开发中",Toast.LENGTH_SHORT).show();

                                    break;

                            }
                        }
                    });
    }

}
