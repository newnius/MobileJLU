package com.newnius.mobileJLU;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.newnius.mobileJLU.regCourse.GetRegCourseTask;
import com.newnius.mobileJLU.regCourse.RegCourse;
import com.newnius.mobileJLU.regCourse.RegCourseDetailActivity;
import com.newnius.mobileJLU.uims.UimsSession;
import com.newnius.mobileJLU.util.ListViewInScrollView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegCourseActivity extends AppCompatActivity {
    private ListViewInScrollView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_course);

        if(!Config.getInCampus()){
            Toast.makeText(RegCourseActivity.this, "没有校外版本", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if(UimsSession.getCookie()==null){
            Toast.makeText(RegCourseActivity.this, "尚未登录", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        listView = (ListViewInScrollView) findViewById(R.id.list_view);
        new GetRegCourseTask(this).execute();
    }

    public void display(List<RegCourse> regCourses){
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (RegCourse course : regCourses) {
            HashMap<String, Object> item= new HashMap<>();
            item.put("lslId", course.getLslId());
            item.put("state", course.isSelected()?"已选":"可选");
            item.put("courseName", course.getCourseName());
            item.put("credit", course.getCredit());
            item.put("classHour", course.getTotalClassHour());
            item.put("type", "(null)");
            item.put("brief", (course.isSelected() ? "[已选]" : "") + course.getCourseName());
            data.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(RegCourseActivity.this, data, R.layout.item_oa,
                new String[]{"brief"}, new int[]{R.id.oaTitle});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {
                        ListView listView = (ListView) parent;
                        HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                        final int lslId = Integer.parseInt(data.get("lslId").toString());
                        String state = data.get("state").toString();
                        String name = data.get("courseName").toString();
                        String credit = data.get("credit").toString();
                        String classHour = data.get("classHour").toString();
                        String type= data.get("type").toString();

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.dialog_reg_course_detail,
                                (ViewGroup) findViewById(R.id.reg_course_detail));
                        AlertDialog dialog = new AlertDialog.Builder(RegCourseActivity.this).setTitle("课程信息").setView(layout)
                                .setPositiveButton("选课", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RegCourseActivity.this, RegCourseDetailActivity.class);

                                        intent.putExtra("lslId", lslId);
                                        startActivity(intent);
                                        Log.i("lslId", lslId+"");
                                    }
                                })
                                .setNegativeButton("取消", null).show();
                        TextView stateView = (TextView) dialog.findViewById(R.id.course_state);
                        stateView.setText(state);
                        TextView nameView = (TextView) dialog.findViewById(R.id.course_name);
                        nameView.setText(name);
                        TextView creditView = (TextView) dialog.findViewById(R.id.course_credit);
                        creditView.setText(credit);
                        TextView classHourView = (TextView) dialog.findViewById(R.id.course_class_hour);
                        classHourView.setText(classHour);
                        TextView typeView = (TextView) dialog.findViewById(R.id.course_type);
                        typeView.setText(type);
                    }
                });

    }
}
