package com.newnius.mobileJLU;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by newnius on 15-12-13.
 *
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class CurriculumGetTask extends AsyncTask<Void, Void, Boolean>{
        private final int termId;

    CurriculumGetTask(int termId) {
            this.termId = termId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String cookie = AccountManager.getUimsCookie();
            int userId = 232059;
                    try {
                        URL url = new URL("http://uims.jlu.edu.cn/ntms/service/res.do");
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("Cookie", cookie);
                        conn.connect();

                        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes("{\"tag\":\"teachClassStud@schedule\",\"branch\":\"default\",\"params\":{\"termId\":"+ termId +",\"studId\":"+userId+"}}");
                        out.flush();
                        out.close();

                        InputStream is = conn.getInputStream();

                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String response = "";
                        String readLine = null;
                        while((readLine =br.readLine()) != null){
                            response = response + readLine;
                        }
                        is.close();
                        br.close();
                        conn.disconnect();
                        Log.i("getLatestScore", response);

                        //UimsMsgCourse uimsMsgCourses = new Gson().fromJson(response, UimsMsgCourse.class);
                        //uimsCourses = uimsMsgCourses.getValue();
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }


        }

    @Override
    protected void onCancelled() {
    /*    mAuthTask = null;
        showProgress(false);
    */}

        @Override
        protected void onPostExecute(final Boolean success) {
        /*    mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        */}
}
