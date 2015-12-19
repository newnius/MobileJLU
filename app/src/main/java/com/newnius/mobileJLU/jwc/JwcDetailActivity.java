package com.newnius.mobileJLU.jwc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.webkit.WebView;
import android.widget.TextView;

import com.newnius.mobileJLU.R;

public class JwcDetailActivity extends AppCompatActivity {
    private int id;
    private TextView titleView;
    private TextView departmentView;
    private TextView dateView;
    private WebView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwc_deatil);
        titleView = (TextView)findViewById(R.id.title);
        departmentView = (TextView)findViewById(R.id.department);
        dateView = (TextView)findViewById(R.id.time);
        contentView = (WebView)findViewById(R.id.content);

        //contentView.setMovementMethod(ScrollingMovementMethod.getInstance());
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            int id = bundle.getInt("id");
            this.id = id;
            new JwcGetDetailTask(this, id).execute();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void display(JwcAnnouncement announcement){
        try {
            titleView.setText(announcement.getTitle());
            departmentView.setText(announcement.getDepartment());
            dateView.setText(announcement.getDate());/*
            contentView.setText(Html.fromHtml(announcement.getContent()));
            contentView.setMovementMethod(LinkMovementMethod.getInstance());*/

            contentView.getSettings().setDefaultTextEncodingName("UTF -8");
            contentView.loadData(announcement.getContent(), "text/html; charset=UTF-8", null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
