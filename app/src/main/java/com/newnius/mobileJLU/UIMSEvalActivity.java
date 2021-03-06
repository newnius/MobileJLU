package com.newnius.mobileJLU;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newnius.mobileJLU.curriculum.CurriCulumnLesson;
import com.newnius.mobileJLU.curriculum.CurriculumCourse;
import com.newnius.mobileJLU.curriculum.CurriculumGetTask;
import com.newnius.mobileJLU.uims.UIMSEvalController;
import com.newnius.mobileJLU.uims.UimsLoginActivity;
import com.newnius.mobileJLU.uims.UimsSession;

import java.util.List;

public class UIMSEvalActivity extends AppCompatActivity {
    private RelativeLayout relativeLayoutMonday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Config.getInCampus()){
            Toast.makeText(this, "校外暂时无法使用", Toast.LENGTH_SHORT).show();
            finish();
        }else if(UimsSession.getCookie()==null){
            //Toast.makeText(this, "尚未登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UIMSEvalActivity.this, UimsLoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.activity_curriculum);
            UIMSEvalController.doOneKeyEval();
            Toast.makeText(this, "评教完成", Toast.LENGTH_SHORT).show();
            finish();
           //new CurriculumGetTask(this, UimsSession.getTermId()).execute();
        }
    }


    public void display(List<CurriculumCourse> courses){
        try {
            courses = getData(courses);
            int itemHeight = getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
            int marTop = getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
            int marLeft = getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);
            for (CurriculumCourse course : courses) {
                List<CurriCulumnLesson> lessons = course.getLessons();
                for (CurriCulumnLesson lesson : lessons) {
                    if(lesson.getBeginWeek()>UimsSession.getWeek() || lesson.getEndWeek() < UimsSession.getWeek() )continue;
                    TextView tv = new TextView(this);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            itemHeight * lesson.getLength() + marTop * (lesson.getLength() - 1));
                    lp.setMargins(marLeft, (lesson.getStart() - 1) * (itemHeight + marTop) + marTop, 0, 0);

                    tv.setLayoutParams(lp);
                    tv.setGravity(Gravity.TOP);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextSize(12);
                    //tv.setTextColor(getResources().getColor(R.color.courseTextColor));
                    tv.setText(course.getCourseName());
                    tv.setBackgroundColor(getResources().getColor(R.color.gray));
                    //tv.setBackgroundColor(R.color.blue);

                    relativeLayoutMonday = (RelativeLayout)findViewById(R.id.weekday_1 + lesson.getDayOfWeek()-1);
                    relativeLayoutMonday.addView(tv);
                }
            }
            Toast.makeText(this, "当前周课程表加载完毕",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayByWeek(List<CurriculumCourse> courses){
        try {
            courses = getData(courses);
            int itemHeight = getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
            int marTop = getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
            int marLeft = getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);
            for (CurriculumCourse course : courses) {
                List<CurriCulumnLesson> lessons = course.getLessons();
                for (CurriCulumnLesson lesson : lessons) {
                    Log.i("week", UimsSession.getWeek() + "");
                    if(lesson.getBeginWeek()>UimsSession.getWeek() || lesson.getEndWeek() < UimsSession.getWeek() )continue;
                    TextView tv = new TextView(this);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            itemHeight * lesson.getLength() + marTop * (lesson.getLength() - 1));
                    lp.setMargins(marLeft, (lesson.getStart() - 1) * (itemHeight + marTop) + marTop, 0, 0);

                    tv.setLayoutParams(lp);
                    tv.setGravity(Gravity.TOP);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTextSize(12);
                    //tv.setTextColor(getResources().getColor(R.color.courseTextColor));
                    tv.setText(course.getCourseName());
                    tv.setBackgroundColor(getResources().getColor(R.color.gray));
                    //tv.setBackgroundColor(R.color.blue);

                    relativeLayoutMonday = (RelativeLayout)findViewById(R.id.weekday_1 + lesson.getDayOfWeek()-1);
                    relativeLayoutMonday.addView(tv);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private List<CurriculumCourse> getData(List<CurriculumCourse> courses){
        CurriculumCourse[][] coursesArray= new CurriculumCourse[7][];
        for(int i=0;i<7;i++){
            coursesArray[i] = new CurriculumCourse[13];
        }
        for(CurriculumCourse course: courses){

        }


        return courses;
    }

}
