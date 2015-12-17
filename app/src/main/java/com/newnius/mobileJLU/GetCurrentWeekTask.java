package com.newnius.mobileJLU;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newnius.mobileJLU.uims.UimsSession;
import com.newnius.mobileJLU.uims.UimsTerm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by newnius on 15-12-14.
 * term startDta, endData and be accessed, thus to calculate current week
 * get terms
 */
public class GetCurrentWeekTask extends AsyncTask<Void, Void, Boolean> {
    private final String cookie;

    public GetCurrentWeekTask() {
      this.cookie = UimsSession.getCookie();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            URL url = new URL("http://cjcx.jlu.edu.cn/score/action/service_res.php");
            String json = "{\"type\":\"search\",\"res\":\"teachingTerm\",\"orderBy\":\"termId desc\",\"tag\":\"teachingTerm\"}";
            if(Config.getInCampus()){
                url = new URL("http://uims.jlu.edu.cn/ntms/service/res.do");
                json = "{\"type\":\"search\",\"res\":\"teachingTerm\",\"orderBy\":\"termName desc\",\"tag\":\"teachingTerm\",\"branch\":\"default\",\"params\":{}}";
            }
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Cookie", cookie);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(json);
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

            Log.i("getCurrentWeek", response);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode terms = root.path("items");
            if(Config.getInCampus()){
                terms = root.path("value");
            }
            List<UimsTerm> uimsTerms = new ArrayList<>();
            for(JsonNode term:terms){
                int termId = term.path("termId").asInt();
                String termName = term.path("termName").asText();
                UimsTerm uimsTerm = new UimsTerm(termId, termName);
                uimsTerms.add(uimsTerm);

                if(termId == UimsSession.getTermId()){
                    UimsSession.setStartDate(term.path("startDate").asText());
                    Log.i("s", UimsSession.getStartDate());
                    UimsSession.setVacationDate(term.path("vacationDate").asText());
                    Log.i("e",UimsSession.getVacationDate());
                }
            }
            Config.setTerms(uimsTerms);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = dateFormat.parse(UimsSession.getStartDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            Calendar now = Calendar.getInstance();
            UimsSession.setWeek(now.get(Calendar.WEEK_OF_YEAR) - calendar.get(Calendar.WEEK_OF_YEAR) + 1);


            return true;
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
    protected void onPostExecute(Boolean result) {
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
