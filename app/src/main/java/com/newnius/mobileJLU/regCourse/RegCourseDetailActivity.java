package com.newnius.mobileJLU.regCourse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
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

import com.newnius.mobileJLU.R;
import com.newnius.mobileJLU.curriculum.CurriculumCourse;
import com.newnius.mobileJLU.util.ListViewInScrollView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegCourseDetailActivity extends AppCompatActivity {
    private ListViewInScrollView listView;
    private int lslId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_course_detail);
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            lslId = bundle.getInt("lslId");
            listView = (ListViewInScrollView) findViewById(R.id.list_view);
            new GetRegCourseDetailTask(this, lslId).execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void display(List<CurriculumCourse> curriculumCourses){
        try {
            List<HashMap<String, Object>> data = new ArrayList<>();
            for (CurriculumCourse course : curriculumCourses) {
                HashMap<String, Object> item = new HashMap<>();
                item.put("lsltId", course.getLsltId());
                item.put("state", course.isSelected() ? "已选" : "可选");
                item.put("courseName", course.getCourseName());
                item.put("teacher", course.getTeacherName());
                item.put("maxStuCnt", course.getMaxStudCnt());
                item.put("stuCnt", course.getStuCnt());
                item.put("isSelected", course.isSelected() ? "Y":"N");
                item.put("brief", (course.isSelected() ? "[已选]" : "") + course.getCourseName());
                data.add(item);
            }
            SimpleAdapter adapter = new SimpleAdapter(RegCourseDetailActivity.this, data, R.layout.item_oa,
                    new String[]{"brief"}, new int[]{R.id.oaTitle});
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long itemId) {
                            ListView listView = (ListView) parent;
                            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                            String state = data.get("state").toString();
                            String name = data.get("courseName").toString();
                            String teacher = data.get("teacher").toString();
                            String maxStuCnt = data.get("maxStuCnt").toString();
                            String stuCnt = data.get("stuCnt").toString();
                            final int lsltId = Integer.parseInt(data.get("lsltId").toString());
                            final boolean isSelected = data.get("isSelected").toString().equals("Y")?true:false;

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.dialog_reg_course_detail,
                                    (ViewGroup) findViewById(R.id.reg_course_detail));

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegCourseDetailActivity.this);
                            builder.setTitle("课程信息").setView(layout);

                            builder.setPositiveButton(isSelected ? "退选" : "选课", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new RegCourseTask(RegCourseDetailActivity.this, !isSelected, lsltId, false).execute();
                                }
                            }).setNegativeButton("取消", null);

                            if(!isSelected) {
                                builder.setNeutralButton("抢课", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new RegCourseTask(RegCourseDetailActivity.this, true, lsltId, true).execute();
                                    }
                                });
                            }
                            AlertDialog dialog = builder.show();
                            TextView stateView = (TextView) dialog.findViewById(R.id.course_state);
                            stateView.setText(state);
                            TextView nameView = (TextView) dialog.findViewById(R.id.course_name);
                            nameView.setText(name);
                            TextView teacherView = (TextView) dialog.findViewById(R.id.course_teacher);
                            teacherView.setText(teacher);
                            TextView stuCntView = (TextView) dialog.findViewById(R.id.course_available);
                            stuCntView.setText(stuCnt + "/" + maxStuCnt);
                        }
                    });
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public void displaySelectResult(String msg){
        Toast.makeText(RegCourseDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
        new GetRegCourseDetailTask(this, lslId).execute();
    }
}
