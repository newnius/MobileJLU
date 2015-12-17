package com.newnius.mobileJLU;

/**
 * Created by newnius on 15-12-17.
 */
public class Weather{
    private String day;
    private String weekday;
    private String city;
    private double temperatureCurrent;
    private double temperatureHighest;
    private double temperatureLowest;
    private String wind;
    private String windPower;
    private String weather;
    private String weatherIcon;

    public Weather(String day, String weekday, String city, double temperatureCurrent, double temperatureHighest, double temperatureLowest, String wind, String windPower, String weather, String weatherIcon) {
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

    public double getTemperatureCurrent() {
        return temperatureCurrent;
    }

    public double getTemperatureHighest() {
        return temperatureHighest;
    }

    public double getTemperatureLowest() {
        return temperatureLowest;
    }

    public String getWeather() {
        return weather;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }
}
