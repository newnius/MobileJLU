package com.newnius.mobileJLU;

/**
 * Created by newnius on 15-12-17.
 */
public class Weather{
    private String day;
    private String weekday;
    private String city;
    private String temperatureCurrent;
    private String temperatureHighest;
    private String temperatureLowest;
    private String wind;
    private String windPower;
    private String weather;
    private String weatherIcon;

    public Weather(String day, String weekday, String city, String temperatureCurrent, String temperatureHighest, String temperatureLowest, String wind, String windPower, String weather, String weatherIcon) {
        this.day = day;
        this.weekday = weekday;
        this.city = city;
        this.temperatureCurrent = temperatureCurrent;
        this.temperatureHighest = temperatureHighest;
        this.temperatureLowest = temperatureLowest;
        this.wind = wind;
        this.windPower = windPower;
        this.weather = weather;
        this.weatherIcon = weatherIcon;
    }

    public String getDay() {
        return day;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getCity() {
        return city;
    }

    public String getTemperatureCurrent() {
        return temperatureCurrent;
    }

    public String getTemperatureHighest() {
        return temperatureHighest;
    }

    public String getTemperatureLowest() {
        return temperatureLowest;
    }

    public String getWeather() {
        return weather;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }


}
