package com.newnius.mobileJLU;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
          if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
              AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
              builder.setMessage("确认退出吗？");
              builder.setTitle("提示");
              builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                      MainActivity.this.finish();
                  }
              });
              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                  }
              });
              builder.create().show();
          }
          return false;
    }

    private void loadModules(){
        int[] moduleIcons = {R.drawable.icon_oa, R.drawable.icon_oa,R.drawable.icon_library,
                R.drawable.icon_card,R.drawable.icon_job,R.drawable.icon_mark,
                R.drawable.icon_curriculum,R.drawable.icon_link,R.drawable.icon_lost,
                R.drawable.icon_oa};
        String[] moduleIds = {"oa", "jwc", "library", "regCourse", "job", "uims", "curriculumSchedule", "link", "lost", "map","news"};
        String[] moduleNames = {"校内通知", "教务通知", "选课", "校园卡", "就业信息", "成绩查询", "课程表", "吉大黄页", "失物招领", "校园地图","吉大新闻"};
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

                            if(!Config.getCanAccessInternet() && !Config.getInCampus()){
                                Toast.makeText(MainActivity.this, "网络不通，请检查",Toast.LENGTH_SHORT).show();
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


                                case "regCourse":
                                    Intent intentRegCourse = new Intent(MainActivity.this, CurriculumActivity.class);
                                    startActivity(intentRegCourse);
                                    break;

                                case "uims":
                                    Intent intentUIms = new Intent(MainActivity.this, UimsActivity.class);
                                    startActivity(intentUIms);
                                    break;

                                case "jwc":
                                    Intent intentJwc = new Intent(MainActivity.this, JwcActivity.class);
                                    startActivity(intentJwc);
                                    break;

                                case "job":
                                    Intent intentJob = new Intent(MainActivity.this, JobActivity.class);
                                    startActivity(intentJob);
                                    break;

                                default:
                                    Toast.makeText(MainActivity.this,data.get("moduleName").toString()+"版块开发中",Toast.LENGTH_SHORT).show();

                                    break;

                            }
                        }
                    });
    }

}
