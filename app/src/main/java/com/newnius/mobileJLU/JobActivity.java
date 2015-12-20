package com.newnius.mobileJLU;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.newnius.mobileJLU.job.JobGetTask;
import com.newnius.mobileJLU.util.Announcement;

import java.util.List;

public class JobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        new JobGetTask(this, 1,1).execute();
    }

    public void display(List<Announcement> announcements){
        Log.i("job", announcements.size() + "");
    }
}
