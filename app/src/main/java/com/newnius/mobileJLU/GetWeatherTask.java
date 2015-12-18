package com.newnius.mobileJLU;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by newnius on 15-12-17.
 */
public class GetWeatherTask extends AsyncTask<Void, Void, Weather>{
    private Main2Activity main2Activity;
    public GetWeatherTask(Main2Activity main2Activity) {
        this.main2Activity = main2Activity;
    }

    @Override
    protected Weather doInBackground(Void... params) {
        try {
            String ip = getMyIp();
            URL url = new URL("http://api.k780.com:88/?app=weather.today&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json&weaid=" + ip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

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

            Log.i("getWeather", response);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            int success = root.path("success").asInt();
            if(success==1){
                JsonNode result = root.path("result");
                String day = result.path("days").asText();
                String weekday = result.path("weekday").asText();
                String city = result.path("citynm").asText();
                String temperatureCurrent = result.path("temperature_curr").asText();
                String temperatureHighest = result.path("temp_high").asText();
                String temperatureLowest = result.path("temp_low").asText();
                String wind = result.path("wind").asText();
                String windPower = result.path("windp").asText();
                String weather = result.path("weather").asText();
                String weatherIcon = result.path("weather_icon").asText();
                Weather w = new Weather( day, weekday, city, temperatureCurrent, temperatureHighest, temperatureLowest, wind, windPower, weather, weatherIcon);
                return w;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Weather w) {
        main2Activity.displayWeather(w);
    }

    private String getMyIp(){
        return "49.140.1.1";
    }
}
