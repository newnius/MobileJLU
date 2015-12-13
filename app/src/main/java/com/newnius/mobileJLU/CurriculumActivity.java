package com.newnius.mobileJLU;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CurriculumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AccountManager.getUimsCookie()==null){
            Toast.makeText(this, "尚未登录", Toast.LENGTH_SHORT).show();
            finish();
        }else {

            setContentView(R.layout.activity_curriculum);

            new CurriculumGetTask(129).execute();
        }
    }
}
